
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Auditor;
import domain.Manager;
import domain.Note;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NoteServiceTest extends AbstractTest {

	//Service under test
	@Autowired
	private NoteService		noteService;

	//Other required Services

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private TripService		tripService;

	//Working Variables
	private Trip			trip1;
	private Manager			manager1;


	@Before
	public void setUpWorkingVariables() {

		this.authenticate("manager1");

		this.manager1 = this.managerService.findByUserAccount(LoginService.getPrincipal());

		final List<Trip> managerByManager1 = new ArrayList<Trip>(this.tripService.findAllManagedBy(this.manager1));

		this.trip1 = managerByManager1.get(0);

		this.unauthenticate();
	}
	@Test
	public void testCreate() {

		this.authenticate("auditor1");

		final Auditor me = this.auditorService.findByUserAccount(LoginService.getPrincipal());

		final Note testNote = this.noteService.create();

		Assert.isNull(testNote.getRemark());
		Assert.notNull(testNote.getWrittenMoment());
		Assert.isTrue(testNote.getWrittenMoment().before(new Date()));
		Assert.isNull(testNote.getReply());
		Assert.isNull(testNote.getReplyMoment());
		Assert.isTrue(testNote.getAuditor().equals(me));
		Assert.isNull(testNote.getTrip());

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {

		this.authenticate("auditor1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);

		final List<Note> auditorNotes = new ArrayList<Note>(auditor.getNotes());
		final Note note = auditorNotes.get(0);
		final Note foundNote = this.noteService.findOne(note.getId());

		Assert.notNull(foundNote);
		Assert.isTrue(note.equals(foundNote));

		this.unauthenticate();

	}
	@Test
	public void testFindAll() {

		this.authenticate("auditor1");

		final Collection<Note> allNotes = new HashSet<Note>();

		for (final Trip t : this.tripService.findAll())
			allNotes.addAll(t.getNotes());

		final Collection<Note> foundNotes = this.noteService.findAll();

		Assert.notNull(foundNotes);
		Assert.isTrue(allNotes.containsAll(foundNotes));
		Assert.isTrue(foundNotes.containsAll(allNotes));
		Assert.isTrue(allNotes.size() == foundNotes.size());

		this.unauthenticate();

	}
	@Test
	public void testSave() {

		this.authenticate("auditor1");

		final Note testNote = this.noteService.create();

		testNote.setRemark("Better ways to comute are needed!");
		testNote.setTrip(this.trip1);

		final Note savedNote = this.noteService.save(testNote);

		Assert.notNull(savedNote);
		Assert.isTrue(testNote.getAuditor().equals(savedNote.getAuditor()));
		Assert.isTrue(testNote.getTrip().equals(savedNote.getTrip()));
		Assert.isTrue(testNote.getWrittenMoment().equals(savedNote.getWrittenMoment()));
		Assert.isTrue(testNote.getRemark().equals(savedNote.getRemark()));
		Assert.isTrue(testNote.getReply() == savedNote.getReply());
		Assert.isTrue(testNote.getReplyMoment() == savedNote.getReplyMoment());
		Assert.isTrue(savedNote.equals(this.noteService.findOne(savedNote.getId())));

		this.unauthenticate();
	}

	@Test
	public void testSaveSuspiciousRemark() {

		this.authenticate("auditor1");

		final Auditor me = this.auditorService.findByUserAccount(LoginService.getPrincipal());
		me.setIsSuspicious(false);

		final Note testNote = this.noteService.create();

		testNote.setRemark("Better ways to comute are needed! SeX hereeeeeee");
		testNote.setTrip(this.trip1);

		this.noteService.save(testNote);

		Assert.isTrue(me.getIsSuspicious());

		this.unauthenticate();
	}

	@Test
	public void testSaveSuspiciousReply() {

		this.authenticate("auditor1");

		final Note testNote = this.noteService.create();

		testNote.setRemark("Better ways to comute are needed!");
		testNote.setTrip(this.trip1);

		final Note savedNote = this.noteService.save(testNote);

		this.unauthenticate();

		this.authenticate("manager1");
		final Manager me = this.managerService.findByUserAccount(LoginService.getPrincipal());
		me.setIsSuspicious(false);

		savedNote.setReply("Sehr gut meine Freunde! viagrA");

		this.noteService.writeReply(savedNote);

		Assert.isTrue(me.getIsSuspicious());

		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testModifyAuditor() {

		this.authenticate("auditor1");

		final Note testNote = this.noteService.create();

		testNote.setRemark("Better ways to comute are needed!");
		testNote.setTrip(this.trip1);

		final Note savedNote = this.noteService.save(testNote);

		savedNote.setRemark("Modified!!");

		this.noteService.save(savedNote);

		this.unauthenticate();
	}
	@Test
	public void testWriteReply() {

		this.authenticate("auditor1");

		final Note testNote = this.noteService.create();

		testNote.setRemark("Better ways to comute are needed!");
		testNote.setTrip(this.trip1);

		final Note savedNote = this.noteService.save(testNote);

		this.unauthenticate();

		this.authenticate("manager1");

		savedNote.setReply("Sehr gut meine Freunde!");

		final Note modifiedNote = this.noteService.writeReply(savedNote);

		Assert.notNull(modifiedNote);
		Assert.isTrue(savedNote.getAuditor().equals(modifiedNote.getAuditor()));
		Assert.isTrue(savedNote.getTrip().equals(modifiedNote.getTrip()));
		Assert.isTrue(savedNote.getWrittenMoment().equals(modifiedNote.getWrittenMoment()));
		Assert.isTrue(savedNote.getRemark().equals(modifiedNote.getRemark()));
		Assert.notNull(modifiedNote.getReply());
		Assert.isTrue(savedNote.getReply().equals(modifiedNote.getReply()));
		Assert.notNull(modifiedNote.getReplyMoment());
		Assert.isTrue(modifiedNote.getReplyMoment().before(new Date()));
		Assert.isTrue(modifiedNote.equals(this.noteService.findOne(modifiedNote.getId())));

		this.unauthenticate();
	}

	@Test
	public void testFindByCurrentAuditor() {
		this.authenticate("auditor1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);

		final Collection<Note> auditorNotes = auditor.getNotes();
		final Collection<Note> foundNotes = this.noteService.findByCurrentAuditor();

		Assert.notNull(foundNotes);
		Assert.isTrue(auditorNotes.containsAll(foundNotes));
		Assert.isTrue(foundNotes.containsAll(auditorNotes));
		Assert.isTrue(auditorNotes.size() == foundNotes.size());

		this.unauthenticate();
	}

	@Test
	public void testFindByCurrentManager() {
		this.authenticate("manager1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		final Collection<Note> managerNotes = new HashSet<Note>();

		for (final Trip t : manager.getTrips())
			managerNotes.addAll(t.getNotes());

		final Collection<Note> foundNotes = this.noteService.findByCurrentManager();

		Assert.notNull(foundNotes);
		Assert.isTrue(managerNotes.containsAll(foundNotes));
		Assert.isTrue(foundNotes.containsAll(managerNotes));
		Assert.isTrue(managerNotes.size() == foundNotes.size());

		this.unauthenticate();
	}

	@Test
	public void testFindByAuditorAndCurrentManager() {
		this.authenticate("auditor1");

		final UserAccount userAccountAuditor = LoginService.getPrincipal();
		final Auditor auditor = this.auditorService.findByUserAccount(userAccountAuditor);
		final Collection<Note> auditorNotes = auditor.getNotes();

		this.unauthenticate();

		this.authenticate("manager1");

		final Collection<Note> managerNotes = this.noteService.findByCurrentManager();

		managerNotes.retainAll(auditorNotes);

		final Collection<Note> foundNotes = this.noteService.findByCurrentManager();

		Assert.notNull(foundNotes);
		Assert.isTrue(managerNotes.containsAll(foundNotes));
		Assert.isTrue(foundNotes.containsAll(managerNotes));
		Assert.isTrue(managerNotes.size() == foundNotes.size());

		this.unauthenticate();
	}
}
