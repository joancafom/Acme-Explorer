
package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
