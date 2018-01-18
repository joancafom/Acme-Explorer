
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import security.LoginService;
import security.UserAccountRepository;
import utilities.AbstractTest;
import domain.Audit;
import domain.Auditor;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AuditServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private AuditService			auditService;

	// Supporting repositories -------------

	@Autowired
	private UserAccountRepository	userAccountRepository;

	@Autowired
	private AuditRepository			auditRepository;

	// Supporting services -----------------

	@Autowired
	private TripService				tripService;

	@Autowired
	private AuditorService			auditorService;


	// Tests -------------------------------

	@Test
	public void testCreate() {
		final Audit audit;
		Date currentMoment;

		currentMoment = new Date();

		this.authenticate("auditor2");

		audit = this.auditService.create();

		Assert.notNull(audit.getMoment());
		Assert.isTrue(audit.getMoment().before(currentMoment));

		Assert.isNull(audit.getTitle());
		Assert.isNull(audit.getDescription());

		Assert.notNull(audit.getAttachments());
		Assert.isTrue(audit.getAttachments().isEmpty());

		Assert.notNull(audit.getIsFinal());
		Assert.isTrue(!audit.getIsFinal());

		Assert.notNull(audit.getAuditor());
		Assert.isTrue(audit.getAuditor().getUserAccount().equals(this.userAccountRepository.findByUsername("auditor2")));

		Assert.isNull(audit.getTrip());

		this.unauthenticate();
	}

	@Test
	public void testSave() {
		Audit audit = null;
		final Collection<Audit> audits;

		this.authenticate("auditor1");

		audits = this.auditRepository.findAll();

		for (final Audit a : audits)
			if (!a.getIsFinal()) {
				audit = a;
				break;
			}

		final String title = "Title";
		final String description = "Description";
		final Collection<String> attachments = new ArrayList<String>();
		attachments.add("http://www.attachment.com");

		audit.setAttachments(attachments);
		audit.setDescription(description);
		audit.setTitle(title);

		final Audit auditS = this.auditService.save(audit);

		Assert.isTrue(auditS.getAttachments().containsAll(attachments));
		Assert.isTrue(auditS.getDescription().equals(description));
		Assert.isTrue(auditS.getTitle().equals(title));

		this.unauthenticate();
	}

	@Test
	public void testDelete() {
		Auditor auditor;
		Audit audit = null;

		this.authenticate("auditor1");

		auditor = this.auditorService.findByUserAccount(LoginService.getPrincipal());

		for (final Audit a : auditor.getAudits()) {
			audit = a;
			break;
		}

		this.auditService.delete(audit);

		Assert.isTrue(this.auditRepository.findOne(audit.getId()) == null);

	}

	@Test
	public void testFindByAuditor() {
		Auditor auditor;
		final Collection<Audit> audits = new HashSet<Audit>();

		this.authenticate("auditor1");

		auditor = this.auditorService.findByUserAccount(LoginService.getPrincipal());
		for (final Audit a : this.auditRepository.findAll())
			if (a.getAuditor().equals(auditor))
				audits.add(a);
		Assert.isTrue(auditor.getAudits().containsAll(this.auditService.findByCurrentAuditor()) && auditor.getAudits().size() == this.auditService.findByCurrentAuditor().size());

	}

	@Test
	public void testFindAll() {
		final Collection<Audit> audits = new HashSet<Audit>();
		for (final Trip t : this.tripService.findAll())
			audits.addAll(t.getAudits());

		Assert.isTrue(audits.containsAll(this.auditService.findAll()) && audits.size() == this.auditService.findAll().size());
	}

	@Test
	public void testFindOne() {
		Audit audit = new Audit();
		for (final Trip t : this.tripService.findAll())
			for (final Audit a : t.getAudits()) {
				audit = a;
				break;
			}

		Assert.isTrue(audit.equals(this.auditService.findOne(audit.getId())));
	}
	@Test
	public void testFindByTrip() {
		Collection<Audit> audits;
		Trip trip = new Trip();

		for (final Trip t : this.tripService.findAll()) {
			trip = t;
			break;
		}

		audits = new HashSet<Audit>(trip.getAudits());
		Assert.isTrue(this.auditService.findByTrip(trip).containsAll(audits) && audits.size() == this.auditService.findByTrip(trip).size());
	}
}
