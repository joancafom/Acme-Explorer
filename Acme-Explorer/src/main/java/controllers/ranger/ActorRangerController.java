
package controllers.ranger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.RangerService;
import controllers.AbstractController;
import domain.Ranger;

@Controller
@RequestMapping("/actor/ranger")
public class ActorRangerController extends AbstractController {

	/* Services */
	@Autowired
	private RangerService	rangerService;


	public ActorRangerController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final Ranger actor;

		final UserAccount userAccount = LoginService.getPrincipal();
		actor = this.rangerService.findByUserAccount(userAccount);

		result = new ModelAndView("ranger/display");
		result.addObject("actor", actor);
		result.addObject("socialIDs", actor.getSocialIDs());

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

	private ModelAndView createEditModelAndView(final Ranger actor) {
		ModelAndView result;
		result = this.createEditModelAndView(actor, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Ranger actor, final Object object) {
		ModelAndView result;
		result = new ModelAndView("ranger/edit");
		result.addObject("ranger", actor);
		result.addObject("actorClassName", "ranger");
		return result;
	}
}
