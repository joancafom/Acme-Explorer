
package services;

import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TripApplicationRepository;
import security.LoginService;
import security.UserAccount;
import domain.ApplicationStatus;
import domain.CreditCard;
import domain.Explorer;
import domain.Manager;
import domain.Message;
import domain.PriorityLevel;
import domain.Trip;
import domain.TripApplication;

@Service
@Transactional
public class TripApplicationService {

	//Managed Repository
	@Autowired
	private TripApplicationRepository	applicationRepository;

	//External Services
	@Autowired
	private ManagerService				managerService;

	@Autowired
	private ExplorerService				explorerService;

	@Autowired
	private MessageService				messageService;


	public TripApplicationService() {
		super();
	}

	/* CRUD */

	public TripApplication create(final Trip t) {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(t);

		final Explorer e = this.explorerService.findByUserAccount(userAccount);

		Boolean explorerFirstTripApplication = true;

		for (final TripApplication tA : e.getTripApplications())
			if (tA.getTrip().equals(t)) {
				explorerFirstTripApplication = false;
				break;
			}

		Assert.isTrue(explorerFirstTripApplication);

		final TripApplication tripApplication = new TripApplication();

		tripApplication.setTrip(t);
		tripApplication.setExplorer(e);

		final long nowMillis = System.currentTimeMillis() - 1000;
		final Date createMoment = new Date(nowMillis);

		tripApplication.setMoment(createMoment);
		tripApplication.setStatus(ApplicationStatus.PENDING);

		e.getTripApplications().add(tripApplication);
		t.getTripApplications().add(tripApplication);

		return tripApplication;
	}

	public TripApplication save(final TripApplication application) {
		Assert.notNull(application);
		final LocalDate now = new LocalDate();
		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		if (manager != null) {
			final TripApplication oldApplication = this.applicationRepository.findOne(application.getId());
			Assert.isTrue(!oldApplication.getStatus().equals(ApplicationStatus.DUE));
			Assert.isTrue(!oldApplication.getStatus().equals(ApplicationStatus.REJECTED));
		}

		if (application.getCreditCard() != null)
			Assert.isTrue((now.getYear() == application.getCreditCard().getYear() && now.getMonthOfYear() == application.getCreditCard().getMonth()) || (now.getYear() < application.getCreditCard().getYear())
				|| (now.getYear() == application.getCreditCard().getYear() && now.getMonthOfYear() < application.getCreditCard().getMonth()));

		return this.applicationRepository.save(application);
	}
	public Collection<TripApplication> findAll() {
		return this.applicationRepository.findAll();
	}

	public TripApplication findOne(final int id) {
		return this.applicationRepository.findOne(id);
	}

	/* Business Methods */

	public Collection<TripApplication> findByCurrentExplorer() {
		final UserAccount userAccount = LoginService.getPrincipal();

		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);
		final Collection<TripApplication> applications = this.applicationRepository.findTripApplicationsByExplorer(explorer.getId());
		return applications;
	}

	public Collection<TripApplication> findAcceptedByCurrentExplorer() {
		final UserAccount userAccount = LoginService.getPrincipal();

		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);
		final Collection<TripApplication> applications = this.applicationRepository.findAcceptedTripApplicationsByExplorer(explorer.getId());
		return applications;
	}

	public void changeApplicationStatus(final TripApplication application, final ApplicationStatus status) {
		//Version 0.3 by JA

		final UserAccount userAccount = LoginService.getPrincipal();

		Assert.notNull(userAccount);
		Assert.notNull(application);
		Assert.notNull(status);

		final Message notification_me = this.messageService.create();
		final Message notification_other = this.messageService.create();
		notification_me.setBody("The Application with ID: " + application.getId() + " was changed from " + application.getStatus() + " to " + status);
		notification_me.setPriority(PriorityLevel.LOW);
		notification_me.setSubject("Change in an associated Application of a Trip");
		notification_other.setBody(notification_me.getBody());
		notification_other.setPriority(notification_me.getPriority());
		notification_other.setSubject(notification_me.getSubject());

		//An application can only change its status if a Manager or its Explorer decide to.

		final Manager manager = this.managerService.findByUserAccount(userAccount);
		final Explorer explorer;

		//We test if it is a Manager the one trying to change it
		if (manager != null) {

			//We test that he manages the associated Trip 
			Assert.isTrue(application.getTrip().getManager().equals(this.managerService.findByUserAccount(userAccount)));
			//He can do it if the application status was Pending
			Assert.isTrue(application.getStatus().equals(ApplicationStatus.PENDING));
			//To either Rejected or Due
			Assert.isTrue(status.equals(ApplicationStatus.REJECTED) || status.equals(ApplicationStatus.DUE));
			//If it were Rejected, the application must have a rejection reason
			if (status.equals(ApplicationStatus.REJECTED))
				Assert.notNull(application.getRejectionReason());

			notification_me.setSender(manager);
			notification_me.setRecipient(manager);

			notification_other.setSender(manager);
			notification_other.setRecipient(application.getExplorer());

		} else {

			//If it is not a manager, it MUST be an Explorer
			explorer = this.explorerService.findByUserAccount(userAccount);

			Assert.notNull(explorer);

			//And it has to be his/her application
			Assert.isTrue(application.getExplorer().equals(explorer));

			//The status must be either DUE or ACCEPTED
			Assert.isTrue(application.getStatus().equals(ApplicationStatus.DUE) || application.getStatus().equals(ApplicationStatus.ACCEPTED));

			//If it were DUE, it can change to ACCEPTED if it has a CreditCard
			if (application.getStatus().equals(ApplicationStatus.DUE)) {
				Assert.isTrue(application.getCreditCard() != null);
				Assert.isTrue(status.equals(ApplicationStatus.ACCEPTED));
			} else {
				//If not, it should be like ACCEPTED->CANCELLED
				application.getStatus().equals(ApplicationStatus.ACCEPTED);
				Assert.isTrue(status.equals(ApplicationStatus.CANCELLED));
			}

			notification_me.setSender(explorer);
			notification_me.setRecipient(explorer);
			notification_other.setSender(explorer);
			notification_other.setRecipient(application.getTrip().getManager());

		}

		this.messageService.sendNotification(notification_me);
		this.messageService.sendNotification(notification_other);

		application.setStatus(status);

	}
	public Collection<TripApplication> findByCurrentManager() {
		/* OK */
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Manager manager = this.managerService.findByUserAccount(userAccount);
		final Collection<TripApplication> tripApplications = this.applicationRepository.findTripApplicationsManagedByManager(manager.getId());

		return tripApplications;

	}

	public void enterCreditCard(final CreditCard creditCard, final TripApplication tripApplication) {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(creditCard);
		Assert.notNull(tripApplication);

		Assert.isTrue(tripApplication.getStatus().equals(ApplicationStatus.DUE));

		final Explorer e = this.explorerService.findByUserAccount(userAccount);

		Assert.isTrue(e.getTripApplications().contains(tripApplication));

		tripApplication.setCreditCard(creditCard);

	}

	public void cancel(final TripApplication tripApplication) {
		UserAccount userAccount;
		Date currentDate;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		Assert.notNull(tripApplication);

		currentDate = new Date();

		Assert.isTrue(tripApplication.getExplorer().getUserAccount().equals(userAccount));
		Assert.isTrue(tripApplication.getStatus().equals(ApplicationStatus.ACCEPTED));
		Assert.isTrue(tripApplication.getTrip().getStartingDate().after(currentDate));

		tripApplication.setStatus(ApplicationStatus.CANCELLED);

	}

	public TripApplication findByExplorerAndTrip(final Explorer explorer, final Trip trip) {
		Assert.notNull(explorer);
		Assert.notNull(trip);

		return this.applicationRepository.findByExplorerIdAndTripId(explorer.getId(), trip.getId());
	}

	public Collection<TripApplication> findAllByTrip(final Trip trip) {
		Assert.notNull(trip);

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);
		Assert.isTrue(manager.getTrips().contains(trip));

		final Collection<TripApplication> tripApplications = this.applicationRepository.findAllByTrip(trip.getId());
		return tripApplications;
	}
}
