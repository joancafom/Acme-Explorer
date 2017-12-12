
package controllers.explorer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TripApplicationService;
import services.TripService;
import controllers.AbstractController;
import domain.Trip;
import domain.TripApplication;

@Controller
@RequestMapping("tripApplication/explorer")
public class TripApplicationExplorerController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private TripApplicationService	tripApplicationService;

	@Autowired
	private TripService				tripService;


	// Constructors ---------------------------

	public TripApplicationExplorerController() {
		super();
	}

	// Listing --------------------------------

	// Creation -------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView res;
		Trip trip;
		TripApplication tripApplication;

		trip = this.tripService.findOne(1464);
		tripApplication = this.tripApplicationService.create(trip);
		res = this.createEditModelAndView(tripApplication);

		return res;
	}

	// Edition --------------------------------

	// Ancillary methods ----------------------

	protected ModelAndView createEditModelAndView(final TripApplication tripApplication) {
		ModelAndView res;

		res = this.createEditModelAndView(tripApplication, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final TripApplication tripApplication, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("tripApplication/edit");
		res.addObject("tripApplication", tripApplication);

		return res;
	}

}
