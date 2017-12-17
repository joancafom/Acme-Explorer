
package controllers.explorer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ExplorerService;
import controllers.AbstractController;
import domain.Explorer;

@Controller
@RequestMapping("/actor/explorer")
public class ActorExplorerController extends AbstractController {

	/* Services */
	@Autowired
	private ExplorerService	explorerService;


	public ActorExplorerController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final Explorer actor;

		final UserAccount userAccount = LoginService.getPrincipal();
		actor = this.explorerService.findByUserAccount(userAccount);

		result = new ModelAndView("explorer/display");
		result.addObject("actor", actor);
		//result.addObject("socialIDs", actor.getSocialIDs());
		result.addObject("emergencyContacts", actor.getEmergencyContacts());

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Explorer actor;

		final UserAccount userAccount = LoginService.getPrincipal();
		actor = this.explorerService.findByUserAccount(userAccount);

		result = this.createEditModelAndView(actor);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Explorer explorer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(explorer);
		else
			try {
				this.explorerService.save(explorer);
				result = new ModelAndView("redirect:/actor/explorer/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(explorer, "message.commit.error");
			}

		return result;

	}

	private ModelAndView createEditModelAndView(final Explorer actor) {
		ModelAndView result;
		result = this.createEditModelAndView(actor, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Explorer actor, final Object object) {
		ModelAndView result;
		result = new ModelAndView("explorer/edit");
		result.addObject("explorer", actor);
		result.addObject("actorClassName", "explorer");
		return result;
	}
}
