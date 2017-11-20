
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TripRepository;
import security.LoginService;
import security.UserAccount;
import domain.Audition;
import domain.Category;
import domain.Curriculum;
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

@Service
@Transactional
public class TripService {

	// Managed repository ------------------

	@Autowired
	private TripRepository		tripRepository;

	// Supporting services -----------------

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private ExplorerService		explorerService;


	// Constructors ------------------------

	public TripService() {
		super();
	}

	// Simple CRUD methods -----------------

	public Trip create(final Collection<Stage> stages, final LegalText legalText, final Category category, final Ranger ranger) {
		String ticker = "";
		double price = 0.0;
		final List<Sponsorship> sponsorships = new ArrayList<Sponsorship>();
		final List<Story> stories = new ArrayList<Story>();
		final List<Note> notes = new ArrayList<Note>();
		final List<Audition> auditions = new ArrayList<Audition>();
		final List<TripApplication> tripApplications = new ArrayList<TripApplication>();
		final List<TagValue> tagValues = new ArrayList<TagValue>();
		final List<SurvivalClass> survivalClasses = new ArrayList<SurvivalClass>();
		UserAccount userAccount;
		Trip trip;

		userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		trip = new Trip();

		final LocalDate date = new LocalDate();
		final Integer year = new Integer(date.getYear());
		final String yy = new String(year.toString());

		final Integer month = new Integer(date.getMonthOfYear());
		final String mm = new String(month.toString());

		final Integer day = new Integer(date.getDayOfMonth());
		final String dd = new String(day.toString());

		final String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String wwww = "";

		for (int i = 0; i < 4; i++) {
			final Random r = new Random();
			wwww += abc.charAt(r.nextInt(abc.length()));
		}

		ticker = yy.substring(2).toUpperCase() + mm.toUpperCase() + dd.toUpperCase() + "-" + wwww.toUpperCase();

		trip.setTicker(ticker);

		Assert.notNull(stages);

		for (final Stage s : stages) {
			Assert.notNull(s);
			price += s.getPrice();
			s.setTrip(trip);

		}

		trip.setPrice(price);
		trip.setSponsorships(sponsorships);
		trip.setStories(stories);
		trip.setNotes(notes);
		trip.setAuditions(auditions);
		trip.setTripApplications(tripApplications);
		trip.setTagValues(tagValues);

		Assert.notNull(legalText);
		trip.setLegalText(legalText);
		legalText.getTrips().add(trip);

		trip.setStages(stages);

		Assert.notNull(category);
		trip.setCategory(category);
		category.getTrips().add(trip);

		Assert.notNull(ranger);
		trip.setRanger(ranger);
		ranger.getTrips().add(trip);
		trip.setSurvivalClasses(survivalClasses);
		trip.setManager(manager);
		manager.getTrips().add(trip);

		return trip;
	}
	public Collection<Trip> findAll() {
		Collection<Trip> trips;

		Assert.notNull(this.tripRepository);
		trips = this.tripRepository.findAll();
		Assert.notNull(trips);

		return trips;
	}

	public Trip findOne(final int tripId) {
		Trip trip;
		trip = this.tripRepository.findOne(tripId);

		return trip;
	}

	public Trip save(final Trip trip) {
		UserAccount userAccount;
		Date currentDate;

		userAccount = LoginService.getPrincipal();

		Assert.notNull(trip);
		Assert.isTrue(userAccount.equals(trip.getManager().getUserAccount()));

		currentDate = new Date();

		Assert.isTrue(trip.getPublicationDate().after(currentDate));
		Assert.isTrue(trip.getStartingDate().after(currentDate));
		Assert.isTrue(trip.getEndingDate().after(currentDate));

		return this.tripRepository.save(trip);
	}

	public void delete(final Trip trip) {
		UserAccount userAccount;
		Date currentDate;

		userAccount = LoginService.getPrincipal();

		Assert.notNull(trip);
		Assert.isTrue(userAccount.equals(trip.getManager().getUserAccount()));

		currentDate = new Date();

		Assert.isTrue(trip.getPublicationDate().after(currentDate));
		Assert.isTrue(this.tripRepository.exists(trip.getId()));

		this.tripRepository.delete(trip);
	}

	// Other business methods --------------

	public Collection<Trip> browseAllTrips() {

		Collection<Trip> trips;

		trips = this.tripRepository.findAll();

		return trips;
	}

	public Collection<Trip> searchTripsKeyWord(final String keyWord) {

		Collection<Trip> trips;

		trips = this.tripRepository.findKeyWord(keyWord);

		return trips;
	}

	public Collection<Trip> findAllManagedBy(final Manager manager) {

		Collection<Trip> trips;

		Assert.notNull(this.tripRepository);
		trips = this.tripRepository.findAllManagedBy(manager.getId());
		Assert.notNull(trips);

		return trips;

	}

	public void cancel(final Trip trip) {
		Date currentDate;
		Assert.notNull(trip);

		currentDate = new Date();

		Assert.notNull(trip.getCancelationReason());
		Assert.isTrue(trip.getPublicationDate().before(currentDate));
		Assert.isTrue(trip.getStartingDate().after(currentDate));
		Assert.isTrue(trip.getEndingDate().after(currentDate));

		this.tripRepository.save(trip);
	}

	public Curriculum findRangerCurriculum(final Trip trip) {
		Curriculum curriculum;

		Assert.notNull(trip);
		Assert.notNull(trip.getRanger());

		curriculum = this.curriculumService.findByRanger(trip.getRanger());

		Assert.notNull(curriculum);

		return curriculum;
	}

	public Collection<Audition> findAuditions(final Trip trip) {
		Collection<Audition> auditions;

		Assert.notNull(trip);
		Assert.notNull(trip.getAuditions());

		auditions = trip.getAuditions();

		Assert.notNull(auditions);

		return auditions;
	}

	public Collection<Trip> searchTripsFinder(final Finder finder) {
		UserAccount userAccount;
		Explorer explorer;
		Collection<Trip> trips;

		userAccount = LoginService.getPrincipal();
		explorer = this.explorerService.findByUserAccount(userAccount);

		Assert.isTrue(finder.getId() == explorer.getFinder().getId());

		trips = this.tripRepository.findFilterFinder(finder.getMinRange(), finder.getMaxRange(), finder.getMinDate(), finder.getMaxDate(), finder.getKeyword());

		Assert.notNull(trips);

		return trips;

	}

	public Collection<Trip> browseTripsByCategory(final Category category) {
		final Collection<Trip> trips = new HashSet<Trip>();

		Assert.notNull(category);

		trips.addAll(this.tripRepository.findByCategory(category.getId()));

		return trips;

	}
}
