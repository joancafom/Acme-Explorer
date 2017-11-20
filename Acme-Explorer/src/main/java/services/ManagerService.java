
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.ApplicationStatus;
import domain.Auditor;
import domain.Folder;
import domain.Manager;
import domain.Message;
import domain.Note;
import domain.SocialID;
import domain.SurvivalClass;
import domain.Trip;
import domain.TripApplication;

@Service
@Transactional
public class ManagerService {

	//Managed Repository
	@Autowired
	private ManagerRepository		managerRepository;

	//Supporting Services
	@Autowired
	private FolderService			folderService;
	private TripApplicationService	tripApplicationService;
	private NoteService				noteService;


	//Simple CRUD methods
	public Manager create() {

		final Manager manager;
		final UserAccount userAccountManager;
		final List<SocialID> socialIDs = new ArrayList<SocialID>();
		final List<Message> sentMessages = new ArrayList<Message>();
		final List<Message> receivedMessages = new ArrayList<Message>();
		final List<Authority> authorities = new ArrayList<Authority>();
		final List<SurvivalClass> survivalClasses = new ArrayList<SurvivalClass>();
		final List<Trip> trips = new ArrayList<Trip>();
		Authority authority;

		manager = new Manager();

		manager.setIsSuspicious(false);
		manager.setIsBanned(false);
		manager.setSocialIDs(socialIDs);

		final Collection<Folder> systemFolders = this.folderService.createSystemFolders(manager);
		manager.setFolders(systemFolders);
		manager.setSentMessages(sentMessages);
		manager.setReceivedMessages(receivedMessages);
		manager.setSurvivalClasses(survivalClasses);
		manager.setTrips(trips);

		userAccountManager = new UserAccount();

		authority = new Authority();
		authority.setAuthority(Authority.MANAGER);
		authorities.add(authority);

		userAccountManager.setAuthorities(authorities);

		manager.setUserAccount(userAccountManager);

		return manager;
	}

	public Manager save(final Manager m) {

		Assert.notNull(m);

		return this.managerRepository.save(m);
	}

	//Other Business operations

	public void changeApplicationStatus(final TripApplication tripApplication, final ApplicationStatus applicationStatus) {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.findByUserAccount(userAccount);

		Assert.notNull(tripApplication);
		Assert.isTrue(tripApplication.getTrip().getManager().equals(manager));

		this.tripApplicationService.changeApplicationStatus(tripApplication, applicationStatus);

	}

	public Collection<Note> getAuditorTripsNotes(final Auditor auditor) {

		Assert.notNull(auditor);

		return this.noteService.findByAuditorAndCurrentManager(auditor);

	}

	public Collection<Manager> listSuspicious() {

		return this.managerRepository.listSuspicious();
	}

	public void writeNoteReply(final Note note) {

		Assert.notNull(note);

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.findByUserAccount(userAccount);

		Assert.isTrue(manager.getTrips().contains(note.getTrip()));

		this.noteService.writeReply(note);

	}

	public Manager findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		final Manager res = this.managerRepository.findByUserAccountId(userAccount.getId());

		Assert.notNull(res);

		return res;
	}

}
