
package controllers.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ManagerService;
import controllers.AbstractController;
import domain.Manager;

@Controller
@RequestMapping("/actor/manager")
public class ActorManagerController extends AbstractController {

	/* Services */
	@Autowired
	private ManagerService	managerService;


	public ActorManagerController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final Manager actor;

		final UserAccount userAccount = LoginService.getPrincipal();
		actor = this.managerService.findByUserAccount(userAccount);

		result = new ModelAndView("manager/display");
		result.addObject("actor", actor);
		result.addObject("socialIDs", actor.getSocialIDs());

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Manager actor;

		final UserAccount userAccount = LoginService.getPrincipal();
		actor = this.managerService.findByUserAccount(userAccount);

		result = this.createEditModelAndView(actor);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Manager manager, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(manager);
		else
			try {
				this.managerService.save(manager);
				result = new ModelAndView("redirect:/actor/manager/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(manager, "message.commit.error");
			}

		return result;

	}

	protected ModelAndView createEditModelAndView(final Manager actor) {
		ModelAndView result;
		result = this.createEditModelAndView(actor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Manager actor, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("manager/edit");
		result.addObject("manager", actor);
		result.addObject("actorClassName", "manager");
		result.addObject("actionURI", "actor/manager/edit.do");
		result.addObject("messageCode", messageCode);
		return result;
	}
}
