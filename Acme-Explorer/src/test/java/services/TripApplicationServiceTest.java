
package services;

import java.util.ArrayList;
import java.util.Date;
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
import domain.ApplicationStatus;
import domain.CreditCard;
import domain.Explorer;
import domain.Manager;
import domain.TripApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TripApplicationServiceTest extends AbstractTest {

	/* Services under test */
	@Autowired
	private ManagerService			managerService;

	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private TripApplicationService	tripApplicationService;
	@Autowired
	private TripService				tripService;


	//	@Test
	//	public void testCreate() {
	//		super.authenticate("explorer1");
	//		final UserAccount userAccount = LoginService.getPrincipal();
	//		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);
	//
	//		TripApplication tripApplicationC;
	//
	//		Trip trip = new Trip();
	//
	//		final List<TripApplication> tripApplications = new LinkedList<TripApplication>();
	//		for (final Trip t : this.tripService.browseAllTrips())
	//			tripApplications.addAll(t.getTripApplications());
	//		tripApplications.removeAll(explorer.getTripApplications());
	//		trip = tripApplications.get(0).getTrip();
	//
	//		tripApplicationC = this.tripApplicationService.create(trip);
	//
	//		final long nowMillis = System.currentTimeMillis() - 1000;
	//		final Date createMoment = new Date(nowMillis);
	//
	//		Assert.notNull(tripApplicationC);
	//		Assert.isTrue(tripApplicationC.getTrip().equals(trip));
	//		Assert.isTrue(tripApplicationC.getMoment().equals(createMoment));
	//		Assert.isTrue(tripApplicationC.getExplorer().equals(explorer));
	//		Assert.notNull(tripApplicationC.getComments());
	//		Assert.isTrue(tripApplicationC.getComments().isEmpty());
	//		Assert.isNull(tripApplicationC.getCreditCard());
	//		Assert.isTrue(tripApplicationC.getStatus().equals(ApplicationStatus.PENDING));
	//		super.authenticate(null);
	//
	//	}

	//	@Test
	//	public void testSave() {
	//		super.authenticate("explorer1");
	//		final UserAccount userAccount = LoginService.getPrincipal();
	//		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);
	//
	//		Trip trip = new Trip();
	//		final List<TripApplication> tripApplications = new LinkedList<TripApplication>();
	//		for (final Trip t : this.tripService.browseAllTrips())
	//			tripApplications.addAll(t.getTripApplications());
	//		tripApplications.removeAll(explorer.getTripApplications());
	//		trip = tripApplications.get(0).getTrip();
	//		final TripApplication tripApplication = this.tripApplicationService.create(trip);
	//
	//		Assert.isTrue(explorer.getTripApplications().contains(tripApplication));
	//		super.authenticate(null);
	//	}

	//	@Test
	//	public void testFindByCurrentExplorer() {
	//		super.authenticate("explorer1");
	//		final UserAccount userAccount = LoginService.getPrincipal();
	//		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);
	//
	//		final Collection<Trip> trips = this.tripService.browseAllTrips();
	//		final Collection<TripApplication> tripApplications = new HashSet<TripApplication>();
	//		for (final Trip t : trips)
	//			for (final TripApplication tA : t.getTripApplications())
	//				if (tA.getExplorer().equals(explorer))
	//					tripApplications.add(tA);
	//		Assert.isTrue(tripApplications.containsAll(this.tripApplicationService.findByCurrentExplorer()));
	//
	//	}

	//	@Test
	//	public void testFindAcceptedByCurrentExplorer() {
	//		super.authenticate("explorer1");
	//		final UserAccount userAccount = LoginService.getPrincipal();
	//		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);
	//
	//		final Collection<Trip> trips = this.tripService.browseAllTrips();
	//		final Collection<TripApplication> tripApplications = new HashSet<TripApplication>();
	//		for (final Trip t : trips)
	//			for (final TripApplication tA : t.getTripApplications())
	//				if (tA.getExplorer().equals(explorer) && tA.getStatus().equals(ApplicationStatus.ACCEPTED))
	//					tripApplications.add(tA);
	//		Assert.isTrue(tripApplications.containsAll(this.tripApplicationService.findAcceptedByCurrentExplorer()));
	//	}

	@Test
	public void testChangeApplicationStatus() {
		super.authenticate("explorer1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);

		Manager manager = new Manager();

		final ApplicationStatus statusToChange = ApplicationStatus.DUE;
		TripApplication tripStatusToChange = new TripApplication();
		final List<TripApplication> tripApplications = new ArrayList<TripApplication>(explorer.getTripApplications());
		tripStatusToChange = tripApplications.get(0);
		tripStatusToChange.setStatus(ApplicationStatus.PENDING);
		manager = tripStatusToChange.getTrip().getManager();

		super.authenticate(manager.getUserAccount().getUsername());

		this.tripApplicationService.changeApplicationStatus(tripStatusToChange, statusToChange);
		Assert.isTrue(tripStatusToChange.getStatus().equals(ApplicationStatus.DUE));
	}

	//	@Test
	//	public void testFindByCurrentManager() {
	//		super.authenticate("manager1");
	//		final UserAccount userAccount = LoginService.getPrincipal();
	//		final Manager manager = this.managerService.findByUserAccount(userAccount);
	//
	//		final Collection<Trip> trips = this.tripService.browseAllTrips();
	//		final Collection<TripApplication> tripApplications = new HashSet<TripApplication>();
	//		for (final Trip t : trips)
	//			if (t.getManager().equals(manager))
	//				tripApplications.addAll(t.getTripApplications());
	//		Assert.isTrue(tripApplications.containsAll(this.tripApplicationService.findByCurrentManager()));
	//
	//	}

	@Test
	public void testEnterCreditCard() {
		super.authenticate("explorer1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);

		final CreditCard creditCard = new CreditCard();
		creditCard.setHolderName("DANIEL YOUMANS");
		creditCard.setBrandName("Visa");
		creditCard.setNumber("4532510079649114");
		creditCard.setCVV(819);
		creditCard.setMonth(02);
		creditCard.setYear(2018);

		TripApplication tripApplication = new TripApplication();
		for (final TripApplication tA : explorer.getTripApplications())
			if (tA.getStatus().equals(ApplicationStatus.DUE)) {
				tripApplication = tA;
				break;
			}

		this.tripApplicationService.enterCreditCard(creditCard, tripApplication);
		Assert.notNull(tripApplication.getCreditCard());

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testCancel() {
		super.authenticate("explorer2");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);
		final List<TripApplication> tripApplications = new ArrayList<TripApplication>(explorer.getTripApplications());

		final TripApplication tripApplication = tripApplications.get(0);
		tripApplication.setStatus(ApplicationStatus.ACCEPTED);

		tripApplication.getTrip().setStartingDate(new Date(2017, 11, 28));
		tripApplication.getTrip().setEndingDate(new Date(2017, 11, 29));

		this.tripApplicationService.cancel(tripApplication);
		Assert.isTrue(tripApplication.getStatus().equals(ApplicationStatus.CANCELLED));

	}

}
