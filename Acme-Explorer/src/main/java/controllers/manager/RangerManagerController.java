
package controllers.manager;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ManagerService;
import services.RangerService;
import services.TripService;
import controllers.AbstractController;
import domain.Manager;
import domain.Ranger;
import domain.Trip;

@Controller
@RequestMapping("/ranger/manager")
public class RangerManagerController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private RangerService	rangerService;


	// Constructors ---------------------------

	public RangerManagerController() {
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

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);

		Assert.isTrue(trip.getManager().equals(manager) || trip.getPublicationDate().before(new Date()));

		if (ranger.getCurriculum() != null)
			curriculumURI = "curriculum/manager/display.do?curriculumId=" + ranger.getCurriculum().getId();

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
