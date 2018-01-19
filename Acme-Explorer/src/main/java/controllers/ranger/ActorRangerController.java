
package controllers.ranger;

import java.util.Date;

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
import services.RangerService;
import services.TripService;
import controllers.AbstractController;
import domain.Ranger;
import domain.Trip;

@Controller
@RequestMapping("/actor/ranger")
public class ActorRangerController extends AbstractController {

	/* Services */
	@Autowired
	private RangerService	rangerService;

	@Autowired
	private TripService		tripService;


	public ActorRangerController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) final Integer tripId) {
		final ModelAndView result;
		final Trip trip;
		final Ranger actor;
		String curriculumURI = "";
		Boolean b = false;

		if (tripId == null) {
			final UserAccount userAccount = LoginService.getPrincipal();
			actor = this.rangerService.findByUserAccount(userAccount);
		} else {
			trip = this.tripService.findOne(tripId);
			actor = this.rangerService.findOne(trip.getRanger().getId());
			Assert.isTrue(trip.getPublicationDate().before(new Date()));
		}

		if (actor.getCurriculum() != null)
			curriculumURI = "curriculum/ranger/display.do?curriculumId=" + actor.getCurriculum().getId();

		result = new ModelAndView("ranger/display");
		result.addObject("actor", actor);
		result.addObject("socialIDs", actor.getSocialIDs());

		if (actor.getUserAccount().equals(LoginService.getPrincipal()))
			b = true;

		result.addObject("curriculumURI", curriculumURI);
		result.addObject("ownProfile", b);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Ranger actor;

		final UserAccount userAccount = LoginService.getPrincipal();
		actor = this.rangerService.findByUserAccount(userAccount);

		result = this.createEditModelAndView(actor);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Ranger ranger, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(ranger);
		else
			try {
				this.rangerService.save(ranger);
				result = new ModelAndView("redirect:/actor/ranger/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(ranger, "message.commit.error");
			}

		return result;

	}

	protected ModelAndView createEditModelAndView(final Ranger actor) {
		ModelAndView result;
		result = this.createEditModelAndView(actor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Ranger actor, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("ranger/edit");
		result.addObject("ranger", actor);
		result.addObject("actorClassName", "ranger");
		result.addObject("actionURI", "actor/ranger/edit.do");
		result.addObject("messageCode", messageCode);
		return result;
	}
}
