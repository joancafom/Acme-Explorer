
package controllers.auditor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NoteService;
import services.TripService;
import controllers.AbstractController;
import domain.Note;
import domain.Trip;

@Controller
@RequestMapping("/note/auditor")
public class NoteAuditorController extends AbstractController {

	/* Services */
	@Autowired
	private NoteService	noteService;
	@Autowired
	private TripService	tripService;


	public NoteAuditorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Note> notes;

		notes = this.noteService.findByCurrentAuditor();
		Assert.notNull(notes);

		result = new ModelAndView("note/list");
		result.addObject("notes", notes);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int noteId) {
		ModelAndView result;
		Note note;

		note = this.noteService.findOne(noteId);
		Assert.notNull(note);

		result = new ModelAndView("note/display");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Note note;

		note = this.noteService.create();
		Assert.notNull(note);
		result = this.createEditModelAndView(note);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Note note, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(note);
		else
			try {
				this.noteService.save(note);
				result = new ModelAndView("redirect:/note/auditor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(note, "note.commit.error");
			}

		return result;

	}

	protected ModelAndView createEditModelAndView(final Note note) {
		ModelAndView result;
		result = this.createEditModelAndView(note, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Note note, final String messageCode) {
		ModelAndView result;
		final Collection<Trip> trips;

		trips = this.tripService.findAllPublished();

		result = new ModelAndView("note/edit");
		result.addObject("note", note);
		result.addObject("trips", trips);
		result.addObject("requestURI", "note/auditor/edit.do");
		result.addObject("messageCode", messageCode);

		return result;
	}
}
