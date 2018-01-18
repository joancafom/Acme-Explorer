
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Actor;
import domain.Auditor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AuditorServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private AuditorService	auditorService;

	// Supporting repositories -------------

	@Autowired
	private ActorService	actorService;


	// Supporting services -----------------

	// Tests -------------------------------

	@Test
	public void testCreate() {

		this.authenticate("admin");
		final UserAccount userAccount = new UserAccount();
		final Auditor auditor = this.auditorService.create(userAccount);

		Assert.notNull(auditor.getNotes());
		Assert.notNull(auditor.getAudits());
		Assert.isTrue(auditor.getNotes().isEmpty());
		Assert.isTrue(auditor.getAudits().isEmpty());

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {

		this.authenticate("auditor1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);

		final Auditor foundAuditor = this.auditorService.findOne(auditor.getId());

		Assert.notNull(foundAuditor);
		Assert.isTrue(auditor.equals(foundAuditor));

		this.unauthenticate();

	}

	@Test
	public void testFindAll() {

		this.authenticate("admin");

		final Collection<Auditor> allAuditors = new HashSet<Auditor>();

		for (final Actor a : this.actorService.findAll())
			if (a instanceof Auditor)
				allAuditors.add((Auditor) a);

		final Collection<Auditor> foundAuditors = this.auditorService.findAll();

		Assert.notNull(foundAuditors);
		Assert.isTrue(allAuditors.size() == foundAuditors.size());
		Assert.isTrue(allAuditors.containsAll(foundAuditors));
		Assert.isTrue(foundAuditors.containsAll(allAuditors));

		this.unauthenticate();

	}

	@Test
	public void testSave() {

		this.authenticate("admin");

		final List<Auditor> allAuditors = new ArrayList<Auditor>(this.auditorService.findAll());
		final Auditor auditor1 = allAuditors.get(0);

		auditor1.setAddress("Indentation Street");
		auditor1.setEmail("No Nice Things Street");
		auditor1.setIsSuspicious(true);
		auditor1.setName("Taylor");
		auditor1.setSurname("Swift");

		final Auditor savedAuditor = this.auditorService.save(auditor1);

		Assert.notNull(savedAuditor);
		Assert.isTrue(auditor1.getIsSuspicious() == savedAuditor.getIsSuspicious());
		Assert.isTrue(auditor1.getAddress().equals(savedAuditor.getAddress()));
		Assert.isTrue(auditor1.getEmail().equals(savedAuditor.getEmail()));
		Assert.isTrue(auditor1.getSurname().equals(savedAuditor.getSurname()));
		Assert.isTrue(auditor1.getName().equals(savedAuditor.getName()));
		Assert.isTrue(auditor1.getNotes() == null ? (savedAuditor.getNotes() == null) : (auditor1.getNotes().equals(savedAuditor.getNotes())));
		Assert.isTrue(auditor1.getAudits() == null ? (savedAuditor.getAudits() == null) : (auditor1.getAudits().equals(savedAuditor.getAudits())));

		this.unauthenticate();

	}

	@Test
	public void testFindByUserAccount() {

		Auditor auditor1 = null;
		Auditor auditor2 = null;

		this.authenticate("admin");

		final Collection<Auditor> auditors = this.auditorService.findAll();

		for (final Auditor a : auditors)
			if (a != null) {
				auditor1 = a;
				break;
			}

		auditor2 = this.auditorService.findByUserAccount(auditor1.getUserAccount());

		Assert.notNull(auditor2);
		Assert.isTrue(auditor1.equals(auditor2));

		this.unauthenticate();
	}
}
