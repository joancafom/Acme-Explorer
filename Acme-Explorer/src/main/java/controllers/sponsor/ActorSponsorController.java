
package controllers.sponsor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.SponsorService;
import controllers.AbstractController;
import domain.Sponsor;

@Controller
@RequestMapping("/actor/sponsor")
public class ActorSponsorController extends AbstractController {

	/* Services */
	@Autowired
	private SponsorService	sponsorService;


	public ActorSponsorController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final Sponsor actor;

		final UserAccount userAccount = LoginService.getPrincipal();
		actor = this.sponsorService.findByUserAccount(userAccount);

		result = new ModelAndView("sponsor/display");
		result.addObject("actor", actor);
		result.addObject("socialIDs", actor.getSocialIDs());

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Sponsor actor;

		final UserAccount userAccount = LoginService.getPrincipal();
		actor = this.sponsorService.findByUserAccount(userAccount);

		result = this.createEditModelAndView(actor);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsor sponsor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsor);
		else
			try {
				this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:/actor/sponsor/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsor, "message.commit.error");
			}

		return result;

	}

	protected ModelAndView createEditModelAndView(final Sponsor actor) {
		ModelAndView result;
		result = this.createEditModelAndView(actor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsor actor, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("sponsor/edit");
		result.addObject("sponsor", actor);
		result.addObject("actorClassName", "sponsor");
		result.addObject("messageCode", messageCode);
		return result;
	}
}
