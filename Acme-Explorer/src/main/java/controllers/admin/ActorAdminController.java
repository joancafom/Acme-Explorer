
package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.AdminService;
import controllers.AbstractController;
import domain.Admin;

@Controller
@RequestMapping("/actor/admin")
public class ActorAdminController extends AbstractController {

	/* Services */
	@Autowired
	private AdminService	adminService;


	public ActorAdminController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final Admin actor;

		final UserAccount userAccount = LoginService.getPrincipal();
		actor = this.adminService.findByUserAccount(userAccount);

		result = new ModelAndView("admin/display");
		result.addObject("actor", actor);
		result.addObject("socialIDs", actor.getSocialIDs());

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Admin actor;

		final UserAccount userAccount = LoginService.getPrincipal();
		actor = this.adminService.findByUserAccount(userAccount);

		result = this.createEditModelAndView(actor);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Admin admin, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(admin);
		else
			try {
				this.adminService.save(admin);
				result = new ModelAndView("redirect:/actor/admin/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(admin, "message.commit.error");
			}

		return result;

	}

	private ModelAndView createEditModelAndView(final Admin actor) {
		ModelAndView result;
		result = this.createEditModelAndView(actor, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Admin actor, final Object object) {
		ModelAndView result;
		result = new ModelAndView("admin/edit");
		result.addObject("admin", actor);
		result.addObject("actorClassName", "admin");
		return result;
	}
}
