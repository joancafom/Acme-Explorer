
package controllers.ranger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ModelAndView display(@RequestParam(required = false) final Integer rangerId) {
		final ModelAndView result;
		final Ranger actor;
		Boolean b = false;

		if (rangerId == null) {
			final UserAccount userAccount = LoginService.getPrincipal();
			actor = this.rangerService.findByUserAccount(userAccount);
		} else
			actor = this.rangerService.findOne(rangerId);

		result = new ModelAndView("ranger/display");
		result.addObject("actor", actor);
		result.addObject("socialIDs", actor.getSocialIDs());

		if (actor.getUserAccount().equals(LoginService.getPrincipal()))
			b = true;

		result.addObject("curriculumURI", "curriculum/ranger/display.do?curriculumId=" + actor.getCurriculum().getId());
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
