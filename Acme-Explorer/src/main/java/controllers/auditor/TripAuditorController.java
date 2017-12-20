
package controllers.auditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.AuditorService;
import services.CategoryService;
import services.TripService;
import controllers.AbstractController;
import domain.Auditor;
import domain.Category;
import domain.Sponsorship;
import domain.Trip;

@Controller
@RequestMapping("/trip/auditor")
public class TripAuditorController extends AbstractController {

	//Services
	@Autowired
	private TripService		tripService;

	@Autowired
	private CategoryService	categoryService;

	@Autowired
	private AuditorService	auditorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String keyword, @RequestParam(required = false) final Integer categoryId) {
		final ModelAndView res;
		Collection<Trip> trips = null;

		if (keyword == null && categoryId == null)
			trips = this.tripService.findAllPublished();
		else if (keyword != null)
			trips = this.tripService.findByKeyWordPublished(keyword);
		else {
			Assert.notNull(categoryId);
			final Category c = this.categoryService.findOne(categoryId);
			Assert.notNull(c);

			trips = this.tripService.findByCategoryPublished(c);
		}

		res = new ModelAndView("trip/list");
		res.addObject("trips", trips);
		res.addObject("requestURI", "/trip/auditor/list.do");
		res.addObject("actorWS", "auditor/");

		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tripId) {
		final ModelAndView res;
		Trip trip;
		final UserAccount userAccount = LoginService.getPrincipal();
		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);
		Assert.notNull(auditor);

		trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);

		final boolean canAudit = !this.tripService.findAuditorAuditedTrips(auditor).contains(trip);

		final Sponsorship sponsorship = trip.getSponsorships().isEmpty() ? null : trip.getSponsorships().iterator().next();

		res = new ModelAndView("trip/display");
		res.addObject("trip", trip);
		res.addObject("sponsorship", sponsorship);
		res.addObject("stageRequestURI", "trip/auditor/display.do?tripId=" + trip.getId());
		res.addObject("rangerURI", "ranger/auditor/display.do?rangerId=" + trip.getRanger().getId());
		res.addObject("canAudit", canAudit);

		return res;

	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		final ModelAndView res;

		res = new ModelAndView("trip/search");
		res.addObject("actorWS", "auditor/");

		return res;

	}
}
