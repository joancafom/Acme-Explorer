
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
import domain.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ManagerServiceTest extends AbstractTest {

	//Service under test
	@Autowired
	private ManagerService	managerService;

	//Supporting Services

	@Autowired
	private ActorService	actorService;


	//Other required Services

	@Test
	public void testCreate() {

		this.authenticate("admin");
		final UserAccount userAccount = new UserAccount();
		final Manager manager = this.managerService.create(userAccount);

		Assert.notNull(manager.getTrips());
		Assert.notNull(manager.getSurvivalClasses());
		Assert.isTrue(manager.getTrips().isEmpty());
		Assert.isTrue(manager.getSurvivalClasses().isEmpty());

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {

		this.authenticate("manager1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		final Manager foundManager = this.managerService.findOne(manager.getId());

		Assert.notNull(foundManager);
		Assert.isTrue(manager.equals(foundManager));

		this.unauthenticate();

	}

	@Test
	public void testFindAll() {

		this.authenticate("admin");

		final Collection<Manager> allManagers = new HashSet<Manager>();

		for (final Actor a : this.actorService.findAll())
			if (a instanceof Manager)
				allManagers.add((Manager) a);

		final Collection<Manager> foundManagers = this.managerService.findAll();

		Assert.notNull(foundManagers);
		Assert.isTrue(allManagers.size() == foundManagers.size());
		Assert.isTrue(allManagers.containsAll(foundManagers));
		Assert.isTrue(foundManagers.containsAll(allManagers));

		this.unauthenticate();

	}

	@Test
	public void testSave() {

		this.authenticate("admin");

		final List<Manager> allManagers = new ArrayList<Manager>(this.managerService.findAll());
		final Manager manager1 = allManagers.get(0);

		manager1.setAddress("Indentation Street");
		manager1.setEmail("No Nice Things Street");
		manager1.setIsSuspicious(true);
		manager1.setName("Taylor");
		manager1.setSurname("Swift");

		final Manager savedManager = this.managerService.save(manager1);

		Assert.notNull(savedManager);
		Assert.isTrue(manager1.getIsSuspicious() == savedManager.getIsSuspicious());
		Assert.isTrue(manager1.getAddress().equals(savedManager.getAddress()));
		Assert.isTrue(manager1.getEmail().equals(savedManager.getEmail()));
		Assert.isTrue(manager1.getSurname().equals(savedManager.getSurname()));
		Assert.isTrue(manager1.getName().equals(savedManager.getName()));
		Assert.isTrue(manager1.getTrips() == null ? (savedManager.getTrips() == null) : (manager1.getTrips().equals(savedManager.getTrips())));
		Assert.isTrue(manager1.getSurvivalClasses() == null ? (savedManager.getSurvivalClasses() == null) : (manager1.getSurvivalClasses().equals(savedManager.getSurvivalClasses())));

		this.unauthenticate();

	}
	@Test
	public void testSuspicious() {
		this.authenticate("admin");

		final Collection<Manager> suspiciousManagers = new HashSet<Manager>();

		for (final Manager m : this.managerService.findAll())
			if (m.getIsSuspicious())
				suspiciousManagers.add(m);

		final Collection<Manager> foundSuspiciousManagers = this.managerService.findAllSuspicious();

		Assert.notNull(foundSuspiciousManagers);
		Assert.isTrue(suspiciousManagers.containsAll(foundSuspiciousManagers));
		Assert.isTrue(foundSuspiciousManagers.containsAll(suspiciousManagers));
		Assert.isTrue(suspiciousManagers.size() == foundSuspiciousManagers.size());

		this.unauthenticate();
	}
	@Test
	public void testFindByUserAccount() {

		Manager manager1 = null;
		Manager manager2 = null;

		this.authenticate("admin");

		final Collection<Manager> managers = this.managerService.findAll();

		for (final Manager m : managers)
			if (m != null) {
				manager1 = m;
				break;
			}

		manager2 = this.managerService.findByUserAccount(manager1.getUserAccount());

		Assert.notNull(manager2);
		Assert.isTrue(manager1.equals(manager2));

		this.unauthenticate();
	}
}
