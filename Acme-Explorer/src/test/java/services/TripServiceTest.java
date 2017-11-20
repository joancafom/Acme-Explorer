
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.RangerRepository;
import repositories.StageRepository;
import repositories.TripRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Category;
import domain.LegalText;
import domain.Manager;
import domain.Ranger;
import domain.Stage;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TripServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private TripService			tripService;

	// Supporting repositories -------------

	@Autowired
	private StageRepository		stageRepository;

	@Autowired
	private RangerRepository	rangerRepository;

	@Autowired
	private TripRepository		tripRepository;

	// Supporting services -----------------

	@Autowired
	private LegalTextService	legalTextService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private ManagerService		managerService;


	// Tests -------------------------------

	@Test
	public void testCreate() {
		Trip trip;

		this.authenticate("manager1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		final Collection<Stage> stagesR = this.stageRepository.findAll();
		final Collection<LegalText> legalTexts = this.legalTextService.findAll();
		final Collection<Category> categories = this.categoryService.findAll();
		final Collection<Ranger> rangers = this.rangerRepository.findAll();

		Stage stage = null;

		final List<Stage> stages = new ArrayList<Stage>();
		LegalText legalText = null;
		Category category = null;
		Ranger ranger = null;

		for (final Stage s : stagesR) {
			stage = s;
			break;
		}

		stages.add(stage);

		for (final LegalText l : legalTexts) {
			legalText = l;
			break;
		}

		for (final Category c : categories) {
			category = c;
			break;
		}

		for (final Ranger r : rangers) {
			ranger = r;
			break;
		}

		trip = this.tripService.create(stages, legalText, category, ranger);

		Assert.notNull(trip.getTicker());
		Assert.isNull(trip.getTitle());
		Assert.isNull(trip.getDescription());
		Assert.notNull(trip.getPrice());

		double sum = 0d;

		for (final Stage s : trip.getStages())
			sum += s.getPrice();

		Assert.isTrue(trip.getPrice() == sum);
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

		Assert.notNull(trip.getLegalText());
		Assert.isTrue(trip.getLegalText().equals(legalText));
		Assert.isTrue(legalText.getTrips().contains(trip));

		Assert.notNull(trip.getStages());
		Assert.isTrue(trip.getStages().equals(stages));

		for (final Stage s : trip.getStages())
			Assert.isTrue(s.getTrip().equals(trip));

		Assert.notNull(trip.getCategory());
		Assert.isTrue(trip.getCategory().equals(category));
		Assert.isTrue(category.getTrips().contains(trip));

		Assert.notNull(trip.getRanger());
		Assert.isTrue(trip.getRanger().equals(ranger));
		Assert.isTrue(ranger.getTrips().contains(trip));

		Assert.notNull(trip.getSurvivalClasses());
		Assert.isTrue(trip.getSurvivalClasses().isEmpty());

		Assert.notNull(trip.getManager());
		Assert.isTrue(trip.getManager().equals(manager));
		Assert.isTrue(manager.getTrips().contains(trip));

		this.unauthenticate();

	}

	@Test
	public void testFindAll() {
		final Collection<Trip> trips = this.tripService.findAll();
		final Collection<Trip> trips2 = this.tripRepository.findAll();

		Assert.isTrue(trips.containsAll(trips2) && trips.size() == trips2.size());
	}

	@Test
	public void testSearchTripsKeyWord() {
		final String keyword = "Brownie";
		final Collection<Trip> trips = this.tripRepository.findAll();
		final Collection<Trip> tripsk = new ArrayList<Trip>();
		final Collection<Trip> trips2 = this.tripService.searchTripsKeyWord(keyword);

		for (final Trip t : trips)
			if (t.getTicker().contains(keyword) || t.getTitle().contains(keyword) || t.getDescription().contains(keyword))
				tripsk.add(t);

		Assert.isTrue(trips2.containsAll(tripsk) && trips2.size() == tripsk.size());

		this.unauthenticate();
	}

	@Test
	public void testBrowseTripsByCategory() {
		Category category = null;
		final Collection<Trip> trips = this.tripRepository.findAll();
		final Collection<Trip> tripsC = new ArrayList<Trip>();

		int i = 0;
		for (final Category c : this.categoryService.findAll()) {
			i += 1;
			if (i == 2) {
				category = c;
				break;
			}
		}

		final Collection<Trip> trips2 = this.tripService.browseTripsByCategory(category);

		for (final Trip t : trips)
			if (t.getCategory().equals(category))
				tripsC.add(t);

		Assert.isTrue(trips2.containsAll(tripsC) && trips2.size() == tripsC.size());

		this.unauthenticate();
	}

	@Test
	public void testDelete() {
		Trip trip = null;

		for (final Trip t : this.tripRepository.findAll())
			if (t.getPublicationDate().after(new Date())) {
				trip = t;
				break;
			}

		this.authenticate("manager3");

		this.tripService.delete(trip);
		Assert.isTrue(this.tripRepository.findOne(trip.getId()) == null);

		this.unauthenticate();
	}

	@Test
	public void testSave() {
		Trip trip = null;
		Category category = null;

		for (final Trip t : this.tripRepository.findAll())
			if (t.getPublicationDate().after(new Date())) {
				trip = t;
				break;
			}

		this.authenticate("manager3");

		int i = 0;
		for (final Category c : this.categoryService.findAll()) {
			i += 1;
			if (i == 2) {
				category = c;
				break;
			}

		}

		final String description = "Description";
		final String requirements = "Requirements";
		final String title = "Title";

		trip.setCategory(category);
		trip.setDescription(description);
		trip.setRequirements(requirements);
		trip.setTitle(title);

		final Trip tripS = this.tripService.save(trip);

		Assert.isTrue(tripS.getCategory().equals(category));
		Assert.isTrue(tripS.getDescription().equals(description));
		Assert.isTrue(tripS.getRequirements().equals(requirements));
		Assert.isTrue(tripS.getTitle().equals(title));
	}

	@Test
	public void testCancel() {
		Trip trip = null;

		for (final Trip t : this.tripRepository.findAll())
			if (t.getPublicationDate().before(new Date()) && t.getStartingDate().after(new Date())) {
				trip = t;
				break;
			}

		trip.setCancelationReason("Cancelation Reason");

		this.tripService.cancel(trip);

		Assert.notNull(trip.getCancelationReason());

	}

}
