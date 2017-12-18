
package controllers.auditor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.SocialIDService;
import controllers.AbstractController;
import domain.Actor;
import domain.SocialID;

@Controller
@RequestMapping("/socialID/auditor")
public class SocialIDAuditorController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private SocialIDService	socialIDService;


	public SocialIDAuditorController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<SocialID> socialIDs;

		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor actor = this.actorService.findByUserAccount(userAccount);
		Assert.notNull(actor);
		socialIDs = actor.getSocialIDs();

		result = new ModelAndView("auditor/display");
		result.addObject("socialIDs", socialIDs);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		SocialID socialID;

		socialID = this.socialIDService.create();

		result = this.createEditModelAndView(socialID);

		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialIDId) {
		ModelAndView result;
		final SocialID socialID;
		final int id = Integer.valueOf(socialIDId);
		socialID = this.socialIDService.findOne(id);
		Assert.notNull(socialID);
		result = this.createEditModelAndView(socialID);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SocialID socialID, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(socialID);
		else
			try {
				this.socialIDService.save(socialID);
				result = new ModelAndView("redirect:/actor/auditor/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(socialID, "message.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SocialID socialID, final BindingResult binding) {
		ModelAndView result;

		try {
			this.socialIDService.delete(socialID);
			result = new ModelAndView("redirect:/actor/auditor/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(socialID, "message.commit.error");
		}

		return result;
	}

	private ModelAndView createEditModelAndView(final SocialID socialID) {
		ModelAndView result;
		result = this.createEditModelAndView(socialID, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final SocialID socialID, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("socialID/edit");
		result.addObject("socialID", socialID);
		return result;
	}
}
