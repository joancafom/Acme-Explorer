
package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
