
package controllers.manager;

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
import services.ManagerService;
import services.TagService;
import services.TagValueService;
import services.TripService;
import controllers.AbstractController;
import domain.Manager;
import domain.Tag;
import domain.TagValue;
import domain.Trip;

@Controller
@RequestMapping("/tagValue/manager")
public class TagValueManagerController extends AbstractController {

	//Services
	@Autowired
	private TagValueService	tagValueService;

	@Autowired
	private TagService		tagService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private ManagerService	managerService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {

		final ModelAndView res;
		TagValue tagValue;
		Trip trip;
		Manager manager;

		trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);
		final UserAccount userAccount = LoginService.getPrincipal();
		manager = this.managerService.findByUserAccount(userAccount);
		Assert.notNull(manager);
		Assert.isTrue(manager.equals(trip.getManager()));

		tagValue = this.tagValueService.create(trip);

		res = this.createEditModelAndView(tagValue);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tagValueId) {

		final ModelAndView res;
		final TagValue tagValue;
		Manager manager;

		tagValue = this.tagValueService.findOne(tagValueId);

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		manager = this.managerService.findByUserAccount(userAccount);
		Assert.notNull(manager);
		Assert.isTrue(manager.equals(tagValue.getTrip().getManager()));

		res = this.createEditModelAndView(tagValue);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final TagValue tagValue, final BindingResult binding) {

		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(tagValue);
		else
			try {
				this.tagValueService.save(tagValue);
				res = new ModelAndView("redirect:/trip/manager/display.do?tripId=" + tagValue.getTrip().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(tagValue, "tagValue.commit.error");
			}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final TagValue tagValue, final BindingResult binding) {

		ModelAndView res;

		try {
			this.tagValueService.delete(tagValue);
			res = new ModelAndView("redirect:/trip/manager/display.do?tripId=" + tagValue.getTrip().getId());
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(tagValue, "tagValue.commit.error");
		}

		return res;
	}

	//Ancillary Methods

	public ModelAndView createEditModelAndView(final TagValue tagValue) {
		return this.createEditModelAndView(tagValue, null);
	}

	public ModelAndView createEditModelAndView(final TagValue tagValue, final String messageCode) {
		ModelAndView res;
		final Collection<Tag> tags;
		final Trip trip = tagValue.getTrip();
		Assert.notNull(trip);

		tags = this.tagService.findAll();

		res = new ModelAndView("tagValue/edit");
		res.addObject("tagValue", tagValue);
		res.addObject("tags", tags);

		res.addObject("message", messageCode);

		return res;
	}
}
