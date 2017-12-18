
package services;

import java.util.Collection;
import java.util.HashSet;

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
import domain.Explorer;
import domain.Finder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ExplorerServiceTest extends AbstractTest {

	@Autowired
	private ExplorerService	explorerService;
	@Autowired
	private ActorService	actorService;


	@Test
	public void testCreate() {

		super.authenticate("admin1");

		final UserAccount userAccount = new UserAccount();

		final Explorer explorer = this.explorerService.create(userAccount, new Finder());
		Assert.notNull(explorer.getSentMessages());
		Assert.notNull(explorer.getReceivedMessages());
		Assert.notNull(explorer.getEmergencyContacts());
		Assert.notNull(explorer.getStories());
		Assert.notNull(explorer.getSurvivalClasses());
		Assert.notNull(explorer.getTripApplications());
		Assert.notNull(explorer.getFinder());
		this.unauthenticate();

	}

	@Test
	public void testFindByUserAccount() {
		super.authenticate("explorer1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer e = this.explorerService.findByUserAccount(userAccount);
		Assert.isTrue(e.getUserAccount().getUsername().equals("explorer1"));
		this.unauthenticate();

	}

	@Test
	public void testFindOne() {

		this.authenticate("explorer1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);

		final Explorer foundExplorer = this.explorerService.findOne(explorer.getId());

		Assert.notNull(foundExplorer);
		Assert.isTrue(explorer.equals(foundExplorer));

		this.unauthenticate();

	}

	@Test
	public void testFindAll() {

		this.authenticate("admin1");

		final Collection<Explorer> allExplorers = new HashSet<Explorer>();

		for (final Actor a : this.actorService.findAll())
			if (a instanceof Explorer)
				allExplorers.add((Explorer) a);

		final Collection<Explorer> foundExplorers = this.explorerService.findAll();

		Assert.notNull(foundExplorers);
		Assert.isTrue(allExplorers.size() == foundExplorers.size());
		Assert.isTrue(allExplorers.containsAll(foundExplorers));
		Assert.isTrue(foundExplorers.containsAll(allExplorers));

		this.unauthenticate();

	}

}
