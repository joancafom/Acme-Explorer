
package services;

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
import domain.LegalText;
import domain.Message;
import domain.Trip;

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

	@Autowired
	private FolderService	folderService;


	//CRUD

	public Admin save(final Admin admin) {

		Assert.notNull(admin);

		final Admin adminS = this.adminRepository.save(admin);

		if (admin.getId() == 0)
			this.folderService.createSystemFolders(adminS);

		return adminS;
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

	public Collection<Double> getApplicationsPerTripStatistics() {

		return this.adminRepository.applicationsPerTripStatistics();
	}

	public Collection<Double> getTripsPerManagerStatistics() {

		return this.adminRepository.tripsPerManagerStatistics();
	}

	public Collection<Double> getTripPricesStatistics() {

		return this.adminRepository.tripPricesStatistics();
	}

	public Collection<Double> getTripsPerRangerStatistics() {

		return this.adminRepository.tripsPerRangerStatistics();
	}

	public Double getPendingApplicationRatio() {

		return this.adminRepository.pendingApplicationsRatio();
	}

	public Double getDueApplicationRatio() {

		return this.adminRepository.dueApplicationsRatio();
	}

	public Double getAcceptedApplicationRatio() {

		return this.adminRepository.acceptedApplicationsRatio();
	}

	public Double getCancelledApplicationRatio() {

		return this.adminRepository.cancelledApplicationsRatio();
	}

	public Double getCancelledVsOrganisedTripsRatio() {

		return this.adminRepository.cancelledApplicationsRatio();
	}

	public Collection<Trip> getTripsMoreApplicationsOrdered() {

		return this.adminRepository.tripsMoreApplicationsOrdered();
	}

	public Integer getNumberReferences(final LegalText legalText) {
		Assert.notNull(legalText);
		return this.adminRepository.numberOfReferencesByLegalTextId(legalText.getId());
	}

	public Collection<Double> getNotesPerTripStatistics() {

		return this.adminRepository.notesPerTripStatistics();
	}

	public Collection<Double> getAuditPerTripStatistics() {

		return this.adminRepository.auditsPerTripStatistics();
	}

	public Double getTripsWithAuditRatio() {

		return this.adminRepository.tripsWithAuditsRatio();
	}

	public Double getRangersWithCurriculumRatio() {

		return this.adminRepository.rangersWithCurriculumRatio();
	}

	public Double getRangersWithERRatio() {

		return this.adminRepository.rangersWithEndorserRecordRatio();
	}

	public Double getSuspiciousManagersRatio() {

		return this.adminRepository.suspiciousManagersRatio();
	}

	public Double getSuspiciousRangersRatio() {

		return this.adminRepository.suspiciousRangersRatio();
	}
}
