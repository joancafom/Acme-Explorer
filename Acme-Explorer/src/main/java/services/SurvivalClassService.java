
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SurvivalClassRepository;
import security.LoginService;
import security.UserAccount;
import domain.Explorer;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;
import domain.TripApplication;

@Service
@Transactional
public class SurvivalClassService {

	//Managed Repository
	@Autowired
	private SurvivalClassRepository	survivalClassRepository;

	//Supporting Services
	@Autowired
	private ManagerService			managerService;

	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private TripApplicationService	tripApplicationService;


	//Simple CRUD operations

	public SurvivalClass create() {

		final UserAccount userAccount = LoginService.getPrincipal();

		final Manager manager = this.managerService.findByUserAccount(userAccount);
		Assert.notNull(manager);

		final SurvivalClass res = new SurvivalClass();

		res.setManager(manager);
		res.setExplorers(new ArrayList<Explorer>());

		manager.getSurvivalClasses().add(res);

		return res;
	}

	public SurvivalClass findOne(final int survivalClassId) {

		return this.survivalClassRepository.findOne(survivalClassId);
	}

	public Collection<SurvivalClass> findAll() {

		return this.survivalClassRepository.findAll();
	}

	public SurvivalClass save(final SurvivalClass survivalClass) {

		Assert.notNull(survivalClass);

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);

		Assert.isTrue(manager != null || explorer != null);
		Assert.isTrue(survivalClass.getMoment().after(new Date()));

		if (manager != null) {
			Assert.isTrue(survivalClass.getManager().equals(manager));
			Assert.isTrue(manager.getTrips().contains(survivalClass.getTrip()));
		}

		return this.survivalClassRepository.save(survivalClass);
	}
	public void delete(final SurvivalClass survivalClass) {

		Assert.notNull(survivalClass);

		final UserAccount userAccount = LoginService.getPrincipal();

		Assert.isTrue(survivalClass.getManager().getUserAccount().equals(userAccount));

		this.survivalClassRepository.delete(survivalClass);
	}

	//Other Business operations

	public Collection<SurvivalClass> findByCurrentManager() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		Assert.notNull(manager);

		return this.survivalClassRepository.findByManagerId(manager.getId());
	}

	public SurvivalClass findOneCurrentManager(final int survivalClassId) {

		final UserAccount userAccount = LoginService.getPrincipal();
		final SurvivalClass survivalClass = this.survivalClassRepository.findOne(survivalClassId);

		Assert.isTrue(survivalClass.getManager().getUserAccount().equals(userAccount));

		return survivalClass;
	}

	public void enroll(final SurvivalClass survivalClass) {

		Assert.notNull(survivalClass);

		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);

		Assert.notNull(explorer);

		Boolean res = false;

		for (final TripApplication ta : this.tripApplicationService.findAcceptedByCurrentExplorer())
			if (ta.getTrip().equals(survivalClass.getTrip())) {
				res = true;
				break;
			}

		Assert.isTrue(survivalClass.getMoment().after(new Date()));
		Assert.isTrue(res);

		explorer.getSurvivalClasses().add(survivalClass);
		survivalClass.getExplorers().add(explorer);

	}

	public Collection<SurvivalClass> findAllByTrip(final Trip trip) {
		Assert.notNull(trip);
		return this.survivalClassRepository.findAllByTripId(trip.getId());
	}
}
