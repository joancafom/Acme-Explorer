
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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

		final Collection<String> comments = new ArrayList<String>();

		tripApplication.setComments(comments);
		
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
		application.setMoment(new Date());
		return this.applicationRepository.save(application);
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
		/* OK */
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		Assert.notNull(application);
		Assert.notNull(status);

		Assert.isTrue(application.getTrip().getManager().equals(this.managerService.findByUserAccount(userAccount)));
		Assert.isTrue(status.equals(ApplicationStatus.REJECTED) || status.equals(ApplicationStatus.DUE));
		Assert.isTrue(application.getStatus().equals(ApplicationStatus.PENDING));
		
		application.setStatus(status);
		this.save(application);
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
}
