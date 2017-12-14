
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TripService;
import domain.Sponsorship;
import domain.Trip;

@Controller
@RequestMapping("/trip")
public class TripController extends AbstractController {

	//Services
	@Autowired
	private TripService	tripService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res;
		final Collection<Trip> trips;

		trips = this.tripService.findAll();
		res = new ModelAndView("trip/list");
		res.addObject("trips", trips);
		res.addObject("requestURI", "/trip/list.do");
		res.addObject("actorWS", "");

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
		res.addObject("stageRequestURI", "stage/list.do?tripId=" + trip.getId());

		return res;

	}
}
