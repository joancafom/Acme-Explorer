
package controllers.auditor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.AuditorService;
import controllers.AbstractController;
import domain.Auditor;

@Controller
@RequestMapping("/actor/auditor")
public class ActorAuditorController extends AbstractController {

	/* Services */
	@Autowired
	private AuditorService	auditorService;


	public ActorAuditorController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final Auditor actor;

		final UserAccount userAccount = LoginService.getPrincipal();
		actor = this.auditorService.findByUserAccount(userAccount);

		result = new ModelAndView("auditor/display");
		result.addObject("actor", actor);
		result.addObject("socialIDs", actor.getSocialIDs());

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Auditor actor;

		final UserAccount userAccount = LoginService.getPrincipal();
		actor = this.auditorService.findByUserAccount(userAccount);

		result = this.createEditModelAndView(actor);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Auditor auditor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(auditor);
		else
			try {
				this.auditorService.save(auditor);
				result = new ModelAndView("redirect:/actor/auditor/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(auditor, "message.commit.error");
			}

		return result;

	}

	protected ModelAndView createEditModelAndView(final Auditor actor) {
		ModelAndView result;
		result = this.createEditModelAndView(actor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Auditor actor, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("auditor/edit");
		result.addObject("auditor", actor);
		result.addObject("actorClassName", "auditor");
		result.addObject("messageCode", messageCode);
		return result;
	}
}
