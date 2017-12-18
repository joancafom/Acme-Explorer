
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
import services.FinderService;
import services.TripService;
import controllers.AbstractController;
import domain.Auditor;
import domain.Category;
import domain.Finder;
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
	private FinderService	finderService;

	@Autowired
	private AuditorService	auditorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String keyword, @RequestParam(required = false) final Integer categoryId, @RequestParam(required = false) final Integer finderId) {

		final ModelAndView res;
		Collection<Trip> trips = null;

		if (keyword == null && categoryId == null && finderId == null)
			trips = this.tripService.findAllPublished();

		else if (keyword == null && categoryId != null && finderId == null) {
			final Category category = this.categoryService.findOne(categoryId);
			trips = this.tripService.findByCategoryPublished(category);

		} else if (keyword != null && categoryId == null && finderId == null)
			trips = this.tripService.findByKeyWordPublished(keyword);

		else if (keyword == null && categoryId == null && finderId != null) {
			final Finder finder = this.finderService.findOne(finderId);
			trips = this.tripService.findByFinderPublished(finder);
		}

		res = new ModelAndView("trip/list");
		res.addObject("trips", trips);
		res.addObject("requestURI", "/trip/list.do");
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
		res.addObject("canAudit", canAudit);

		return res;

	}
}
