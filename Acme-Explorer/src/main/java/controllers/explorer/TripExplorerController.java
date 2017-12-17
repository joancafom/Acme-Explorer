
package controllers.explorer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.FinderService;
import services.TripService;
import controllers.AbstractController;
import domain.Category;
import domain.Finder;
import domain.Sponsorship;
import domain.Trip;

@Controller
@RequestMapping("/trip/explorer")
public class TripExplorerController extends AbstractController {

	//Services
	@Autowired
	private TripService		tripService;

	@Autowired
	private CategoryService	categoryService;

	@Autowired
	private FinderService	finderService;


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
		res.addObject("actorWS", "explorer/");

		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tripId) {
		final ModelAndView res;
		Trip trip;

		trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);

		final Sponsorship sponsorship = trip.getSponsorships().isEmpty() ? null : trip.getSponsorships().iterator().next();

		res = new ModelAndView("trip/display");
		res.addObject("trip", trip);
		res.addObject("sponsorship", sponsorship);
		res.addObject("stageRequestURI", "trip/explorer/display.do?tripId=" + trip.getId());

		return res;

	}

}
