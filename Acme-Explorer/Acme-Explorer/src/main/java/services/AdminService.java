
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdminRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Admin;
import domain.Manager;
import domain.Message;
import domain.Ranger;

@Service
@Transactional
public class AdminService {

	//Managed Repository
	@Autowired
	private AdminRepository	adminRepository;

	//Supporting Services
	@Autowired
	private ManagerService	managerService;

	@Autowired
	private RangerService	rangerService;

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	//Other Business process

	public Manager createManager() {

		final Manager res = this.managerService.create();
		this.managerService.save(res);

		return res;

	}
	public Ranger createRanger() {

		final Ranger res = this.rangerService.create();

		this.rangerService.save(res);

		return res;
	}

	public void broadcastNotification(final Message message) {

		final UserAccount userAccount = LoginService.getPrincipal();

		final Admin me = this.findByUserAccount(userAccount);

		final Collection<Actor> allActorsWithoutMe = this.actorService.findAll();
		allActorsWithoutMe.remove(me);
		for (final Actor a : allActorsWithoutMe)
			this.messageService.sendNotification(a, message);

	}

	public Admin findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		final Admin res = this.adminRepository.findByUserAccountId(userAccount.getId());

		return res;
	}

	public Collection<Object> dashboardInformation() {
		final Collection<Object> res = new ArrayList<Object>();

		res.add(this.adminRepository.applicationsPerTripStatistics());
		res.add(this.adminRepository.tripsPerManagerStatistics());
		res.add(this.adminRepository.tripPricesStatistics());
		res.add(this.adminRepository.tripsPerRangerStatistics());
		res.add(this.adminRepository.pendingApplicationsRatio());
		res.add(this.adminRepository.dueApplicationsRatio());
		res.add(this.adminRepository.acceptedApplicationsRatio());
		res.add(this.adminRepository.cancelledApplicationsRatio());
		res.add(this.adminRepository.cancelledVsOrganisedTripsRatio());
		res.add(this.adminRepository.tripsMoreApplicationsOrdered());
		res.add(this.adminRepository.legalTextsByTrip());
		res.add(this.adminRepository.notesPerTripStatistics());
		res.add(this.adminRepository.auditionsPerTripStatistics());
		res.add(this.adminRepository.tripsWithAuditionsRatio());
		res.add(this.adminRepository.rangersWithCurriculumRatio());
		res.add(this.adminRepository.rangersWithEndorserRecordRatio());
		res.add(this.adminRepository.suspiciousManagersRatio());
		res.add(this.adminRepository.suspiciousRangersRatio());

		return res;
	}
}
