
package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.SponsorshipService;
import services.TripService;
import controllers.AbstractController;
import domain.Sponsorship;
import domain.Trip;

@Controller
@RequestMapping("/sponsorship/manager")
public class SponsorshipManagerController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private TripService			tripService;


	// Constructors ---------------------------

	public SponsorshipManagerController() {
		super();
	}

	// Display --------------------------------

	// Listing --------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tripId) {
		ModelAndView res;
		Trip trip;
		final Collection<Sponsorship> sponsorships;

		trip = this.tripService.findOne(tripId);
		sponsorships = this.sponsorshipService.findByTrip(trip);

		Assert.isTrue(trip.getManager().getUserAccount().equals(LoginService.getPrincipal()));

		res = new ModelAndView("sponsorship/list");
		res.addObject("sponsorships", sponsorships);
		res.addObject("requestURI", "sponsorship/manager/list.do");
		res.addObject("tripURI", "trip/manager/display.do?tripId=");

		return res;
	}
	// Creation -------------------------------

	// Edition --------------------------------

	// Ancillary methods ----------------------

}
