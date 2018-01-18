
package controllers.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ManagerService;
import services.NoteService;
import services.TripService;
import controllers.AbstractController;
import domain.Manager;
import domain.Note;
import domain.Trip;

@Controller
@RequestMapping("/note/manager")
public class NoteManagerController extends AbstractController {

	//Services
	@Autowired
	private NoteService		noteService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private ManagerService	managerService;


	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView res;

		final Trip trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);
		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);
		Assert.notNull(manager);

		Assert.isTrue(trip.getManager().equals(manager));

		res = new ModelAndView("note/list");
		res.addObject("notes", trip.getNotes());
		res.addObject("requestURI", "note/manager/list.do");

		return res;
	}

	//Edit
	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam final int noteId) {
		final ModelAndView res;

		final Note note;
		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);
		Assert.notNull(manager);

		note = this.noteService.findOne(noteId);

		Assert.notNull(note);
		Assert.isTrue(manager.equals(note.getTrip().getManager()));

		res = this.createEditModelAndView(note);

		return res;
	}
	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Note note, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(note);
		else
			try {

				this.noteService.writeReply(note);
				res = new ModelAndView("redirect:/note/manager/list.do?tripId=" + note.getTrip().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(note, "note.commit.error");
			}

		return res;
	}

	//Ancillary Methods
	protected ModelAndView createEditModelAndView(final Note note) {
		return this.createEditModelAndView(note, null);
	}

	protected ModelAndView createEditModelAndView(final Note note, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("note/edit");
		res.addObject("note", note);

		res.addObject("messageCode", messageCode);

		return res;
	}
}
