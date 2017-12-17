
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import domain.Actor;
import domain.SocialID;

@Controller
@RequestMapping("/socialID")
public class SocialIDController extends AbstractController {

	@Autowired
	private ActorService	actorService;


	public SocialIDController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int actorId) {
		final ModelAndView result;
		final Collection<SocialID> socialIDs;
		Actor actor;

		actor = this.actorService.findOne(actorId);
		Assert.notNull(actor);
		socialIDs = actor.getSocialIDs();

		result = new ModelAndView();
		result.addObject("socialIDs", socialIDs);
		return result;

	}
}
