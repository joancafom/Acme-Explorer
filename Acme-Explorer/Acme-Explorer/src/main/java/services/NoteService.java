
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
import domain.Trip;

@Service
@Transactional
public class NoteService {

	//Managed Repository
	@Autowired
	private NoteRepository	noteRepository;

	//Supporting Services
	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private ManagerService	managerService;


	//Supporting Services

	//Simple CRUD operations
	public Note create(final Trip trip) {

		Assert.notNull(trip);

		final UserAccount userAccount = LoginService.getPrincipal();

		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);

		final Note res = new Note();

		final long nowMillis = System.currentTimeMillis() - 1000;
		final Date writtenMoment = new Date(nowMillis);

		res.setWrittenMoment(writtenMoment);
		res.setAuditor(auditor);
		res.setTrip(trip);

		trip.getNotes().add(res);
		auditor.getNotes().add(res);

		return res;
	}

	public Note findOne(final int noteId) {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Note res = this.noteRepository.findOne(noteId);
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		if (manager != null)
			Assert.isTrue(manager.getTrips().contains(res.getTrip()));
		else {
			final Auditor auditor = this.auditorService.findByUserAccount(userAccount);
			Assert.isTrue(res == null || res.getAuditor().equals(auditor));
		}

		return res;
	}

	public Note save(final Note note) {
		final UserAccount userAccount = LoginService.getPrincipal();
		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);
		if (auditor != null)
			Assert.isNull(this.noteRepository.findOne(note.getId()));
		else {
			final Manager manager = this.managerService.findByUserAccount(userAccount);
			Assert.notNull(manager);
			final Note previous = this.noteRepository.findOne(note.getId());
			Assert.notNull(previous);

			Assert.isTrue(manager.getTrips().contains(note.getTrip()));
			Assert.isTrue(note.getWrittenMoment().equals(previous.getWrittenMoment()));
			Assert.isTrue(note.getRemark().equals(previous.getRemark()));
		}

		return this.noteRepository.save(note);
	}

	//Delete is not implemented because a note can not be deleted

	//Other Business

	public Collection<Note> findByCurrentManager() {

		final UserAccount userAccount = LoginService.getPrincipal();

		final Manager manager = this.managerService.findByUserAccount(userAccount);

		return this.noteRepository.findByManagerId(manager.getId());
	}

	public Collection<Note> findByCurrentAuditor() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);

		return this.noteRepository.findByAuditorId(auditor.getId());
	}

	public Collection<Note> findByAuditorAndCurrentManager(final Auditor auditor) {

		Assert.notNull(auditor);

		final UserAccount userAccount = LoginService.getPrincipal();

		final Manager manager = this.managerService.findByUserAccount(userAccount);

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
}
