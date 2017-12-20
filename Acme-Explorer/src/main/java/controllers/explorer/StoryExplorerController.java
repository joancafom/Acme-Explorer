
package controllers.explorer;

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

import security.LoginService;
import security.UserAccount;
import services.ExplorerService;
import services.StoryService;
import services.TripService;
import controllers.AbstractController;
import domain.Explorer;
import domain.Story;
import domain.Trip;

@Controller
@RequestMapping("/story/explorer")
public class StoryExplorerController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private StoryService	storyService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private ExplorerService	explorerService;


	// Constructors ---------------------------

	public StoryExplorerController() {
		super();
	}

	// Display --------------------------------

	// Listing --------------------------------

	// Creation -------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		final Story story;

		story = this.storyService.create();
		res = this.createEditModelAndView(story);

		return res;
	}

	// Edition --------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int storyId) {
		ModelAndView res;
		Story story;

		story = this.storyService.findOne(storyId);
		Assert.notNull(story);

		res = this.createEditModelAndView(story);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Story story, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(story);
		else
			try {
				this.storyService.save(story);
				res = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(story, "story.commit.error");
			}

		return res;
	}

	// Ancillary methods ----------------------

	protected ModelAndView createEditModelAndView(final Story story) {
		ModelAndView res;

		res = this.createEditModelAndView(story, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final Story story, final String messageCode) {
		ModelAndView res;
		Collection<Trip> trips;
		final Explorer explorer;

		final UserAccount userAccount = LoginService.getPrincipal();
		explorer = this.explorerService.findByUserAccount(userAccount);

		trips = this.tripService.findExplorerAcceptedAndOverTrips(explorer.getId());

		res = new ModelAndView("story/edit");
		res.addObject("story", story);
		res.addObject("trips", trips);
		res.addObject("messageCode", messageCode);

		return res;
	}
}
