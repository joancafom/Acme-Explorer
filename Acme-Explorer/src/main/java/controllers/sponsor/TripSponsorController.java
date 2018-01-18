
package controllers.sponsor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.TripService;
import controllers.AbstractController;
import domain.Category;
import domain.Sponsorship;
import domain.Trip;

@Controller
@RequestMapping("/trip/sponsor")
public class TripSponsorController extends AbstractController {

	//Services
	@Autowired
	private TripService		tripService;

	@Autowired
	private CategoryService	categoryService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String keyword, @RequestParam(required = false) final Integer categoryId) {
		final ModelAndView res;
		final Collection<Trip> trips;

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
		res.addObject("requestURI", "/trip/sponsor/list.do");
		res.addObject("actorWS", "sponsor/");

		return res;
	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tripId) {
		final ModelAndView res;
		Trip trip;

		trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);
		
		Assert.isTrue(trip.getPublicationDate().before(new Date()));

		final List<Sponsorship> sponsorships = new ArrayList<Sponsorship>(trip.getSponsorships());
		Collections.shuffle(sponsorships);
		Sponsorship sponsorship = null;
		if (sponsorships.isEmpty() == false)
			sponsorship = sponsorships.get(0);

		res = new ModelAndView("trip/display");
		res.addObject("trip", trip);
		res.addObject("sponsorship", sponsorship);
		res.addObject("stageRequestURI", "stage/list.do?tripId=" + trip.getId());
		res.addObject("rangerURI", "ranger/sponsor/display.do?tripId=" + tripId);
		res.addObject("myTrip", false);

		return res;

	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		final ModelAndView res;

		res = new ModelAndView("trip/search");
		res.addObject("actorWS", "sponsor/");

		return res;

	}
}
