
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
import domain.Message;

@Service
@Transactional
public class AdminService {

	//Managed Repository
	@Autowired
	private AdminRepository	adminRepository;

	//Supporting Services

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	//CRUD

	public Admin save(final Admin admin) {

		Assert.notNull(admin);
		return this.adminRepository.save(admin);
	}

	//Other Business process

	public void broadcastNotification(final Message message) {

		final UserAccount userAccount = LoginService.getPrincipal();

		final Admin me = this.findByUserAccount(userAccount);

		final Collection<Actor> allActorsWithoutMe = this.actorService.findAll();
		allActorsWithoutMe.remove(me);
		for (final Actor a : allActorsWithoutMe)
			message.setRecipient(a);
		this.messageService.sendNotification(message);

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
		res.add(this.adminRepository.auditsPerTripStatistics());
		res.add(this.adminRepository.tripsWithAuditsRatio());
		res.add(this.adminRepository.rangersWithCurriculumRatio());
		res.add(this.adminRepository.rangersWithEndorserRecordRatio());
		res.add(this.adminRepository.suspiciousManagersRatio());
		res.add(this.adminRepository.suspiciousRangersRatio());

		return res;
	}
}
