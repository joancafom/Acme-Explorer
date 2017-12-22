
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import security.LoginService;
import security.UserAccount;
import domain.Auditor;
import domain.Manager;
import domain.Note;
import domain.SystemConfiguration;

@Service
@Transactional
public class NoteService {

	//Managed Repository
	@Autowired
	private NoteRepository				noteRepository;

	//Supporting Services
	@Autowired
	private AuditorService				auditorService;

	@Autowired
	private ManagerService				managerService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	//Supporting Services

	//Simple CRUD operations
	public Note create() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);

		Assert.notNull(auditor);

		final Note res = new Note();

		final long nowMillis = System.currentTimeMillis() - 1000;
		final Date writtenMoment = new Date(nowMillis);

		res.setWrittenMoment(writtenMoment);
		res.setAuditor(auditor);

		auditor.getNotes().add(res);

		return res;
	}

	public Note findOne(final int noteId) {

		final Note res = this.noteRepository.findOne(noteId);

		return res;
	}

	public Collection<Note> findAll() {

		final Collection<Note> res = this.noteRepository.findAll();

		return res;
	}

	public Note save(final Note note) {

		//An Auditor can WRITE a note only
		//A Manager can write a reply to a Note, and hence MODIFY only this fields (not save a new one).

		Assert.notNull(note);
		Assert.notNull(note.getAuditor());
		Assert.notNull(note.getTrip());

		final UserAccount userAccount = LoginService.getPrincipal();
		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);

		Boolean isSuspicious;

		//If it is an Auditor, we check that the Note id = 0 (freshly new)
		if (auditor != null) {
			Assert.isTrue(note.getId() == 0);
			isSuspicious = this.decideSuspiciousness(note.getRemark());

			if (isSuspicious)
				auditor.setIsSuspicious(isSuspicious);

		} else {

			//If it's not an Auditor, it must be a Manager the one who's trying to write a reply
			final Manager manager = this.managerService.findByUserAccount(userAccount);
			Assert.notNull(manager);
			Assert.isTrue(manager.getTrips().contains(note.getTrip()));

			//Hence, reply and replyMoment cannot be null

			Assert.notNull(note.getReply());
			Assert.notNull(note.getReplyMoment());

			isSuspicious = this.decideSuspiciousness(note.getReply());

			if (isSuspicious)
				manager.setIsSuspicious(isSuspicious);

		}

		return this.noteRepository.save(note);
	}
	//Delete is not implemented because a note can not be deleted

	//Other Business

	public Collection<Note> findByCurrentAuditor() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);

		Assert.notNull(auditor);

		return this.noteRepository.findByAuditorId(auditor.getId());
	}

	//A Manager can list the notes of his/her Trips
	public Collection<Note> findByCurrentManager() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		return this.noteRepository.findByManagerId(manager.getId());
	}

	//A Manager can list the Notes an Auditor has written on his/her trips
	public Collection<Note> findByAuditorAndCurrentManager(final Auditor auditor) {

		Assert.notNull(auditor);

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		Assert.notNull(manager);

		return this.noteRepository.findByAuditorAndManagerIds(auditor.getId(), manager.getId());

	}

	public Note writeReply(final Note note) {

		Assert.notNull(note);
		Assert.notNull(note.getReply());

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		Assert.isTrue(manager.getTrips().contains(note.getTrip()));

		final long nowMillis = System.currentTimeMillis() - 1000;
		final Date replyMoment = new Date(nowMillis);

		note.setReplyMoment(replyMoment);

		return this.save(note);
	}

	public Boolean decideSuspiciousness(final String testString) {
		final SystemConfiguration sysConfig = this.systemConfigurationService.getCurrentSystemConfiguration();
		Assert.notNull(sysConfig);

		Boolean res = false;

		for (final String spamWord : sysConfig.getSpamWords())
			if (testString.toLowerCase().contains(spamWord)) {
				res = true;
				break;
			}

		return res;
	}
}
