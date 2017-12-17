
package controllers.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
