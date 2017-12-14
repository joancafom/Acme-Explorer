
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import domain.Audit;
import domain.Category;
import domain.Explorer;
import domain.Finder;
import domain.LegalText;
import domain.Manager;
import domain.Note;
import domain.Ranger;
import domain.Sponsorship;
import domain.Stage;
import domain.Story;
import domain.SurvivalClass;
import domain.TagValue;
import domain.Trip;
import domain.TripApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TripServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private TripService		tripService;

	// Supporting services -----------------

	@Autowired
	private CategoryService	categoryService;

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private ExplorerService	explorerService;

	@Autowired
	private FinderService	finderService;


	// Tests -------------------------------

	@Test
	public void testCreate() {
		Trip trip;

		this.authenticate("manager1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		trip = this.tripService.create();

		Assert.notNull(trip.getTicker());
		Assert.isNull(trip.getTitle());
		Assert.isNull(trip.getDescription());
		Assert.notNull(trip.getStages());

		Assert.isTrue(trip.getPrice() == 0.0);
		Assert.isNull(trip.getPublicationDate());
		Assert.isNull(trip.getStartingDate());
		Assert.isNull(trip.getEndingDate());
		Assert.isNull(trip.getRequirements());
		Assert.isNull(trip.getCancelationReason());

		Assert.notNull(trip.getSponsorships());
		Assert.isTrue(trip.getSponsorships().isEmpty());

		Assert.notNull(trip.getStories());
		Assert.isTrue(trip.getStories().isEmpty());

		Assert.notNull(trip.getNotes());
		Assert.isTrue(trip.getNotes().isEmpty());

		Assert.notNull(trip.getTripApplications());
		Assert.isTrue(trip.getTripApplications().isEmpty());

		Assert.notNull(trip.getTagValues());
		Assert.isTrue(trip.getTagValues().isEmpty());

		Assert.isNull(trip.getLegalText());

		Assert.notNull(trip.getStages());
		Assert.isTrue(trip.getStages().isEmpty());

		Assert.isNull(trip.getCategory());

		Assert.isNull(trip.getRanger());

		Assert.notNull(trip.getSurvivalClasses());
		Assert.isTrue(trip.getSurvivalClasses().isEmpty());

		Assert.notNull(trip.getManager());
		Assert.isTrue(trip.getManager().equals(manager));
		Assert.isTrue(manager.getTrips().contains(trip));

		this.unauthenticate();

	}

	@Test
	public void testFindAll() {
		// REVISAR !!!
		// Cómo se comprueba que el findAll() funciona correctamente?

		final Integer currentNumberOfTripsInTheXML = 4;

		this.authenticate("manager1");

		final Collection<Trip> trips = this.tripService.findAll();

		Assert.notNull(trips);
		Assert.isTrue(trips.size() == currentNumberOfTripsInTheXML);

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {
		Trip trip1 = null;
		Trip trip2 = null;

		this.authenticate("manager1");

		final Collection<Trip> trips = this.tripService.findAll();

		for (final Trip t : trips)
			if (t != null) {
				trip1 = t;
				break;
			}

		trip2 = this.tripService.findOne(trip1.getId());

		Assert.isTrue(trip1.equals(trip2));

		this.unauthenticate();
	}

	@Test
	public void testSave() {
		Trip trip1 = null;
		Trip trip2 = null;

		this.authenticate("manager1");

		final Collection<Trip> trips = this.tripService.findAll();

		for (final Trip t : trips)
			if (t != null) {
				trip1 = t;
				break;
			}

		// El ticker no se puede editar
		trip1.setTitle("Title");
		trip1.setDescription("Description");
		// El price no se puede editar manualmente, depende de los precios de las stages
		// REVISAR !!!
		// Cómo crear una fecha futura con los métodos deprecados?
		trip1.setRequirements("Requirements");
		trip1.setCancelationReason("Cancelation Reason");

		trip1.getSponsorships().add(new Sponsorship());
		trip1.getStories().add(new Story());
		trip1.getNotes().add(new Note());
		trip1.getAudits().add(new Audit());
		trip1.getTripApplications().add(new TripApplication());
		trip1.getTagValues().add(new TagValue());
		trip1.setLegalText(new LegalText());
		trip1.getStages().add(new Stage());
		trip1.setCategory(new Category());
		trip1.setRanger(new Ranger());
		trip1.getSurvivalClasses().add(new SurvivalClass());
		trip1.setManager(new Manager());

		trip2 = this.tripService.save(trip1);

		Assert.notNull(trip2);
		Assert.isTrue(trip1.getTitle().equals(trip2.getTitle()));
		Assert.isTrue(trip1.getDescription().equals(trip2.getDescription()));
		Assert.isTrue(trip1.getRequirements().equals(trip2.getRequirements()));
		Assert.isTrue(trip1.getCancelationReason().equals(trip2.getCancelationReason()));
		Assert.isTrue(trip1.getSponsorships().equals(trip2.getSponsorships()));
		Assert.isTrue(trip1.getStories().equals(trip2.getStories()));
		Assert.isTrue(trip1.getNotes().equals(trip2.getNotes()));
		Assert.isTrue(trip1.getAudits().equals(trip2.getAudits()));
		Assert.isTrue(trip1.getTripApplications().equals(trip2.getTripApplications()));
		Assert.isTrue(trip1.getTagValues().equals(trip2.getTagValues()));
		Assert.isTrue(trip1.getLegalText().equals(trip2.getLegalText()));
		Assert.isTrue(trip1.getStages().equals(trip2.getStages()));
		Assert.isTrue(trip1.getCategory().equals(trip2.getCategory()));
		Assert.isTrue(trip1.getRanger().equals(trip2.getRanger()));
		Assert.isTrue(trip1.getSurvivalClasses().equals(trip2.getSurvivalClasses()));
		Assert.isTrue(trip1.getManager().equals(trip2.getManager()));
	}

	@Test
	public void testDelete() {
		Trip trip = null;

		this.authenticate("manager1");

		final Collection<Trip> trips = this.tripService.findAll();

		for (final Trip t : trips)
			if (t != null) {
				trip = t;
				break;
			}

		this.tripService.delete(trip);
		Assert.isNull(this.tripService.findOne(trip.getId()));

		this.unauthenticate();
	}

	@Test
	public void testFindByKeyWord() {
		this.authenticate("admin1");

		final String keyword = "Brownie";
		final Collection<Trip> trips = this.tripService.findAll();
		final Collection<Trip> tripsKeyWord1 = new ArrayList<Trip>();
		final Collection<Trip> tripsKeyWord2 = this.tripService.findByKeyWord(keyword);

		for (final Trip t : trips)
			if (t.getTicker().contains(keyword) || t.getTitle().contains(keyword) || t.getDescription().contains(keyword))
				tripsKeyWord1.add(t);

		Assert.isTrue(tripsKeyWord2.containsAll(tripsKeyWord1));
		Assert.isTrue(tripsKeyWord2.size() == tripsKeyWord1.size());

		this.unauthenticate();
	}

	@Test
	public void testFindByCategory() {
		this.authenticate("admin1");

		Category category = null;

		int i = 0;
		for (final Category c : this.categoryService.findAll()) {
			i += 1;
			if (i == 2) {
				category = c;
				break;
			}
		}

		final Collection<Trip> trips = this.tripService.findAll();
		final Collection<Trip> tripsCategory1 = new ArrayList<Trip>();
		final Collection<Trip> tripsCategory2 = this.tripService.findByCategory(category);

		for (final Trip t : trips)
			if (t.getCategory().equals(category))
				tripsCategory1.add(t);

		Assert.isTrue(tripsCategory2.containsAll(tripsCategory1));
		Assert.isTrue(tripsCategory2.size() == tripsCategory1.size());

		this.unauthenticate();
	}

	@Test
	public void testCancel() {
		Trip trip = null;

		for (final Trip t : this.tripService.findAll())
			if (t.getPublicationDate().before(new Date()) && t.getStartingDate().after(new Date())) {
				trip = t;
				break;
			}

		Assert.notNull(trip);

		trip.setCancelationReason("Cancelation Reason");

		this.tripService.cancel(trip);

		Assert.notNull(this.tripService.findOne(trip.getId()).getCancelationReason());
	}

	@Test
	public void testFindByFinder() {
		this.authenticate("explorer2");

		final Explorer explorer = this.explorerService.findByUserAccount(LoginService.getPrincipal());
		final Finder finder = explorer.getFinder();

		// Saturday, 11 de December de 2010 17:40:32
		finder.setMinDate(new Date(1292089232000L));
		// Friday, 11 de December de 2020 17:40:32
		finder.setMaxDate(new Date(1607708432000L));
		finder.setMaxRange(20000.99);
		finder.setMinRange(0.00);
		finder.setKeyword("r");
		final Finder savedFinder = this.finderService.save(finder);

		final Collection<Trip> trips = this.tripService.findAll();
		final Collection<Trip> tripsFinder1 = new ArrayList<Trip>();
		final Collection<Trip> tripsFinder2 = this.tripService.findByFinder(savedFinder);

		for (final Trip t : trips)
			if (t.getPrice() > savedFinder.getMinRange() && t.getPrice() < savedFinder.getMaxRange() && t.getStartingDate().after(savedFinder.getMinDate()) && t.getEndingDate().before(savedFinder.getMaxDate()))
				tripsFinder1.add(t);

		Assert.isTrue(tripsFinder2.containsAll(tripsFinder1));
		Assert.isTrue(tripsFinder2.size() == tripsFinder1.size());

		this.unauthenticate();
	}
}
