
package controllers.sponsor;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RangerService;
import services.TripService;
import controllers.AbstractController;
import domain.Ranger;
import domain.Trip;

@Controller
@RequestMapping("/ranger/sponsor")
public class RangerSponsorController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private RangerService	rangerService;

	@Autowired
	private TripService		tripService;


	// Constructors ---------------------------

	public RangerSponsorController() {
		super();
	}

	// Display --------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tripId) {
		final ModelAndView res;
		final Trip trip;
		Ranger ranger;
		String curriculumURI = "";

		trip = this.tripService.findOne(tripId);
		ranger = this.rangerService.findOne(trip.getRanger().getId());

		Assert.isTrue(trip.getPublicationDate().before(new Date()));

		if (ranger.getCurriculum() != null)
			curriculumURI = "curriculum/sponsor/display.do?curriculumId=" + ranger.getCurriculum().getId();

		res = new ModelAndView("ranger/display");
		res.addObject("actor", ranger);
		res.addObject("curriculumURI", curriculumURI);
		res.addObject("ownProfile", false);

		return res;
	}

	// Creation -------------------------------

	// Edition --------------------------------

	// Ancillary methods ----------------------

}
