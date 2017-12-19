
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TripRepository;
import security.LoginService;
import security.UserAccount;
import domain.Audit;
import domain.Auditor;
import domain.Category;
import domain.Explorer;
import domain.Finder;
import domain.Manager;
import domain.Note;
import domain.Sponsorship;
import domain.Stage;
import domain.Story;
import domain.SurvivalClass;
import domain.SystemConfiguration;
import domain.TagValue;
import domain.Trip;
import domain.TripApplication;

@Service
@Transactional
public class TripService {

	// Managed repository ------------------

	@Autowired
	private TripRepository				tripRepository;

	// Supporting services -----------------

	@Autowired
	private ManagerService				managerService;

	@Autowired
	private ExplorerService				explorerService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors ------------------------

	public TripService() {
		super();
	}

	// Simple CRUD methods -----------------

	public Trip create() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);
		Assert.notNull(manager);

		String ticker = "";
		final double price = 0.0;
		final List<Stage> stages = new ArrayList<Stage>();
		final List<Sponsorship> sponsorships = new ArrayList<Sponsorship>();
		final List<Story> stories = new ArrayList<Story>();
		final List<Note> notes = new ArrayList<Note>();
		final List<Audit> audits = new ArrayList<Audit>();
		final List<TripApplication> tripApplications = new ArrayList<TripApplication>();
		final List<TagValue> tagValues = new ArrayList<TagValue>();
		final List<SurvivalClass> survivalClasses = new ArrayList<SurvivalClass>();
		Trip trip;

		trip = new Trip();

		ticker = this.systemConfigurationService.getTickerAndUpdateNext();

		trip.setTicker(ticker);
		trip.setManager(manager);
		trip.setPrice(price);
		trip.setSponsorships(sponsorships);
		trip.setStories(stories);
		trip.setNotes(notes);
		trip.setAudits(audits);
		trip.setTripApplications(tripApplications);
		trip.setTagValues(tagValues);
		trip.setStages(stages);
		trip.setSurvivalClasses(survivalClasses);

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
		// REVISAR !!!
		// Debe tener algún assert?
		Trip trip;

		trip = this.tripRepository.findOne(tripId);

		return trip;
	}

	public Trip save(final Trip trip) {
		Assert.notNull(trip);

		final Date currentDate = new Date();
		Assert.isTrue(trip.getPublicationDate().after(currentDate) || trip.getCancelationReason() != null);
		Assert.isTrue(trip.getPublicationDate().before(trip.getStartingDate()));
		Assert.isTrue(trip.getEndingDate().after(trip.getStartingDate()));

		return this.tripRepository.save(trip);
	}

	public void delete(final Trip trip) {
		Assert.notNull(trip);

		final Date currentDate = new Date();
		Assert.isTrue(trip.getPublicationDate().after(currentDate));
		Assert.isTrue(this.tripRepository.exists(trip.getId()));

		this.tripRepository.delete(trip);
	}

	// Other business methods --------------

	public Collection<Trip> findByKeyWord(final String keyWord) {
		Assert.notNull(keyWord);

		Collection<Trip> trips;

		Assert.notNull(this.tripRepository);
		trips = this.tripRepository.findByKeyWord(keyWord);
		Assert.notNull(trips);

		return trips;
	}

	// REVISAR !!!
	// Qué significa navegar el tree de categories?
	public Collection<Trip> findByCategory(final Category category) {
		Assert.notNull(category);

		final Collection<Trip> trips;

		Assert.notNull(this.tripRepository);
		trips = this.tripRepository.findByCategoryId(category.getId());
		Assert.notNull(trips);

		return trips;
	}

	public void cancel(final Trip trip) {
		Assert.notNull(trip);

		Date currentDate;
		currentDate = new Date();

		Assert.notNull(trip.getCancelationReason());
		Assert.isTrue(trip.getPublicationDate().before(currentDate));
		Assert.isTrue(trip.getStartingDate().after(currentDate));
		Assert.isTrue(trip.getEndingDate().after(currentDate));

		this.tripRepository.save(trip);
	}

	public Collection<Trip> findAllManagedBy(final Manager manager) {
		Assert.notNull(manager);

		Collection<Trip> trips;

		Assert.notNull(this.tripRepository);
		trips = this.tripRepository.findAllManagedBy(manager.getId());
		Assert.notNull(trips);

		return trips;
	}

	public Collection<Trip> findByFinderPublished(final Finder finder) {
		Assert.notNull(finder);

		Explorer explorer;
		Collection<Trip> trips = null;

		explorer = this.explorerService.findByUserAccount(LoginService.getPrincipal());

		Assert.isTrue(finder.getId() == explorer.getFinder().getId());

		final SystemConfiguration sysConfig = this.systemConfigurationService.getCurrentSystemConfiguration();
		Assert.notNull(sysConfig);

		if (finder.getKeyword() == null && finder.getMinRange() == null && finder.getMaxRange() == null && finder.getMinDate() == null && finder.getMaxDate() == null) {
			final Page<Trip> tripPage = this.tripRepository.findAllPublished(new PageRequest(0, sysConfig.getMaxNumResults()));
			trips = tripPage.getContent();

		} else if (finder.getKeyword() != null && finder.getMinRange() == null && finder.getMaxRange() == null && finder.getMinDate() == null && finder.getMaxDate() == null) {
			final Page<Trip> tripPage = this.tripRepository.findByKeyWordPublished(new PageRequest(0, sysConfig.getMaxNumResults()), finder.getKeyword());
			trips = tripPage.getContent();

		} else if (finder.getKeyword() != null && finder.getMinRange() != null && finder.getMaxRange() != null && finder.getMinDate() == null && finder.getMaxDate() == null) {
			final Page<Trip> tripPage = this.tripRepository.findByKeyWordAndRangePublished(new PageRequest(0, sysConfig.getMaxNumResults()), finder.getMinRange(), finder.getMaxRange(), finder.getKeyword());
			trips = tripPage.getContent();

		} else if (finder.getKeyword() != null && finder.getMinRange() == null && finder.getMaxRange() == null && finder.getMinDate() != null && finder.getMaxDate() != null) {
			final Page<Trip> tripPage = this.tripRepository.findByKeyWordAndDatePublished(new PageRequest(0, sysConfig.getMaxNumResults()), finder.getMinDate(), finder.getMaxDate(), finder.getKeyword());
			trips = tripPage.getContent();

		} else if (finder.getKeyword() != null && finder.getMinRange() != null && finder.getMaxRange() != null && finder.getMinDate() != null && finder.getMaxDate() != null) {
			final Page<Trip> tripPage = this.tripRepository.findByFinderAttributesPublished(new PageRequest(0, sysConfig.getMaxNumResults()), finder.getMinRange(), finder.getMaxRange(), finder.getMinDate(), finder.getMaxDate(), finder.getKeyword());
			trips = tripPage.getContent();
		}

		Assert.notNull(trips);

		return trips;
	}
	public Collection<Trip> findAllPublished() {
		Collection<Trip> trips;

		Assert.notNull(this.tripRepository);
		trips = this.tripRepository.findAllPublished2();
		Assert.notNull(trips);

		return trips;
	}

	public Collection<Trip> findByCategoryPublished(final Category category) {
		Assert.notNull(category);

		final Collection<Trip> trips;

		Assert.notNull(this.tripRepository);
		trips = this.tripRepository.findByCategoryIdPublished(category.getId());
		Assert.notNull(trips);

		return trips;
	}

	public Collection<Trip> findByKeyWordPublished(final String keyWord) {
		Assert.notNull(keyWord);

		Collection<Trip> trips;

		Assert.notNull(this.tripRepository);
		trips = this.tripRepository.findByKeyWordPublished2(keyWord);
		Assert.notNull(trips);

		return trips;
	}

	public Collection<Trip> findAuditorAuditedTrips(final Auditor auditor) {
		Assert.notNull(auditor);
		final Collection<Trip> res;

		res = this.tripRepository.findAuditorAuditedTrips(auditor.getId());

		return res;
	}

	public Collection<Trip> findExplorerAcceptedAndOverTrips(final int explorerId) {
		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);

		Assert.notNull(explorer);
		Assert.isTrue(explorer.getId() == explorerId);

		Collection<Trip> trips;

		Assert.notNull(this.tripRepository);
		trips = this.tripRepository.findExplorerAcceptedAndOverTrips(explorerId);

		return trips;
	}
}
