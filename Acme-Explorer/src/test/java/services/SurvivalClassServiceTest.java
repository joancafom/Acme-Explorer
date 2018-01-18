
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import domain.Explorer;
import domain.Location;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SurvivalClassServiceTest extends AbstractTest {

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private TripService				tripService;


	@Test
	public void testCreate() {

		this.authenticate("manager1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		final SurvivalClass survivalClass = this.survivalClassService.create();

		Assert.notNull(survivalClass);
		Assert.isTrue(survivalClass.getManager().equals(manager));
		Assert.isNull(survivalClass.getTitle());
		Assert.isNull(survivalClass.getDescription());
		Assert.isNull(survivalClass.getMoment());
		Assert.isNull(survivalClass.getLocation());
		Assert.isNull(survivalClass.getTrip());
		Assert.notNull(survivalClass.getExplorers());
		Assert.isTrue(survivalClass.getExplorers().isEmpty());

		this.unauthenticate();

	}

	@Test
	public void testSave() {

		this.authenticate("manager1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		final List<Trip> trips = new ArrayList<Trip>(manager.getTrips());
		final Trip trip = trips.get(0);
		final Location etsii = new Location();
		etsii.setCoordinateX(-0.988);
		etsii.setCoordinateY(9.876);
		etsii.setName("ETSII");

		final SurvivalClass testSurvivalClass = this.survivalClassService.create();
		testSurvivalClass.setTitle("How to get away with 3rd grade of Software Engineering");
		testSurvivalClass.setDescription("Don't enroll this survivalClass if you want to remain alive");
		testSurvivalClass.setLocation(etsii);
		testSurvivalClass.setMoment(new Date(1858707446000L));
		testSurvivalClass.setTrip(trip);

		final SurvivalClass savedSurvivalClass = this.survivalClassService.save(testSurvivalClass);

		Assert.notNull(savedSurvivalClass);
		Assert.isTrue(testSurvivalClass.getTitle().equals(savedSurvivalClass.getTitle()));
		Assert.isTrue(testSurvivalClass.getDescription().equals(savedSurvivalClass.getDescription()));
		Assert.notNull(savedSurvivalClass.getLocation());
		Assert.isTrue(testSurvivalClass.getLocation().getName().equals(savedSurvivalClass.getLocation().getName()));
		Assert.isTrue(testSurvivalClass.getLocation().getCoordinateX() == savedSurvivalClass.getLocation().getCoordinateX());
		Assert.isTrue(testSurvivalClass.getLocation().getCoordinateY() == savedSurvivalClass.getLocation().getCoordinateY());
		Assert.isTrue(testSurvivalClass.getMoment().equals(savedSurvivalClass.getMoment()));
		Assert.isTrue(testSurvivalClass.getTrip().equals(savedSurvivalClass.getTrip()));
		Assert.isTrue(this.survivalClassService.findAll().contains(savedSurvivalClass));

		this.unauthenticate();

	}

	@Test
	public void testDelete() {

		this.authenticate("manager1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		final List<Trip> trips = new ArrayList<Trip>(manager.getTrips());
		final Trip trip = trips.get(0);
		final Location etsii = new Location();
		etsii.setCoordinateX(-0.988);
		etsii.setCoordinateY(9.876);
		etsii.setName("ETSII");

		final SurvivalClass testSurvivalClass = this.survivalClassService.create();
		testSurvivalClass.setTitle("How to get away with 3rd grade of Software Engineering");
		testSurvivalClass.setDescription("Don't enroll this survivalClass if you want to remain alive");
		testSurvivalClass.setLocation(etsii);
		testSurvivalClass.setMoment(new Date(1858707446000L));
		testSurvivalClass.setTrip(trip);

		final SurvivalClass savedSurvivalClass = this.survivalClassService.save(testSurvivalClass);

		this.survivalClassService.delete(savedSurvivalClass);

		Assert.isNull(this.survivalClassService.findOne(savedSurvivalClass.getId()));

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {

		this.authenticate("manager1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager mananger = this.managerService.findByUserAccount(userAccount);

		final List<SurvivalClass> scs = new ArrayList<SurvivalClass>(mananger.getSurvivalClasses());
		final SurvivalClass sc = scs.get(0);
		final SurvivalClass foundSurvivalClass = this.survivalClassService.findOne(sc.getId());

		Assert.notNull(foundSurvivalClass);
		Assert.isTrue(sc.equals(foundSurvivalClass));

		this.unauthenticate();

	}

	@Test
	public void testFindAll() {

		this.authenticate("manager1");

		final Collection<SurvivalClass> allScs = new HashSet<SurvivalClass>();

		for (final Trip t : this.tripService.findAll())
			allScs.addAll(t.getSurvivalClasses());

		final Collection<SurvivalClass> foundSurvivalClasses = this.survivalClassService.findAll();

		Assert.notNull(foundSurvivalClasses);
		Assert.isTrue(allScs.size() == foundSurvivalClasses.size());
		Assert.isTrue(allScs.containsAll(foundSurvivalClasses));
		Assert.isTrue(foundSurvivalClasses.containsAll(allScs));

		this.unauthenticate();

	}

	@Test
	public void testFindByCurrentManager() {

		this.authenticate("manager1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		final List<SurvivalClass> survivalClasses = new ArrayList<SurvivalClass>(manager.getSurvivalClasses());
		final List<SurvivalClass> foundSc = new ArrayList<SurvivalClass>(this.survivalClassService.findByCurrentManager());

		Assert.notNull(foundSc);
		Assert.isTrue(survivalClasses.containsAll(foundSc));
		Assert.isTrue(foundSc.containsAll(survivalClasses));

		this.unauthenticate();
	}
	@Test
	public void testFindOneByCurrentManager() {

		this.authenticate("manager1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager mananger = this.managerService.findByUserAccount(userAccount);

		final List<SurvivalClass> scs = new ArrayList<SurvivalClass>(mananger.getSurvivalClasses());
		final SurvivalClass sc = scs.get(0);
		final SurvivalClass foundSurvivalClass = this.survivalClassService.findOneCurrentManager(sc.getId());

		Assert.notNull(foundSurvivalClass);
		Assert.isTrue(sc.equals(foundSurvivalClass));

		this.unauthenticate();

	}

	@Test
	public void testEnrroll() {

		this.authenticate("manager1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		final List<Trip> trips = new ArrayList<Trip>(manager.getTrips());
		final Trip trip = trips.get(0);
		final Location etsii = new Location();
		etsii.setCoordinateX(-0.988);
		etsii.setCoordinateY(9.876);
		etsii.setName("ETSII");

		final SurvivalClass testSurvivalClass = this.survivalClassService.create();
		testSurvivalClass.setTitle("How to get away with 3rd grade of Software Engineering");
		testSurvivalClass.setDescription("Don't enroll this survivalClass if you want to remain alive");
		testSurvivalClass.setLocation(etsii);
		testSurvivalClass.setMoment(new Date(1858707446000L));
		testSurvivalClass.setTrip(trip);

		final SurvivalClass savedSurvivalClass = this.survivalClassService.save(testSurvivalClass);

		this.unauthenticate();

		this.authenticate("explorer1");

		final UserAccount userAccountE = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccountE);

		this.survivalClassService.enroll(savedSurvivalClass);

		Assert.isTrue(explorer.getSurvivalClasses().contains(savedSurvivalClass));
		Assert.isTrue(savedSurvivalClass.getExplorers().contains(explorer));

		this.unauthenticate();
	}
}
