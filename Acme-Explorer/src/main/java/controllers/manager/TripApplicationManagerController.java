
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

import services.TripApplicationService;
import services.TripService;
import controllers.AbstractController;
import domain.Trip;
import domain.TripApplication;

@Controller
@RequestMapping("/tripApplication/manager")
public class TripApplicationManagerController extends AbstractController {

	/* Services */
	@Autowired
	private TripApplicationService	tripApplicationService;
	@Autowired
	private TripService				tripService;


	public TripApplicationManagerController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final Integer tripApplicationId) {
		final ModelAndView result;
		TripApplication tripApplication;

		Assert.notNull(tripApplicationId);
		tripApplication = this.tripApplicationService.findOne(tripApplicationId);

		result = new ModelAndView("tripApplication/display");
		result.addObject("tripApplication", tripApplication);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer tripId) {
		final ModelAndView result;
		final Collection<TripApplication> tripApplications;

		if (tripId == null)
			tripApplications = this.tripApplicationService.findByCurrentManager();
		else {
			final Trip trip = this.tripService.findOne(tripId);
			tripApplications = this.tripApplicationService.findAllByTrip(trip);
		}

		result = new ModelAndView("tripApplication/list");
		result.addObject("tripApplications", tripApplications);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tripApplicationId) {
		final ModelAndView result;
		TripApplication tripApplication;

		Assert.notNull(tripApplicationId);
		tripApplication = this.tripApplicationService.findOne(tripApplicationId);

		result = this.createEditModelAndView(tripApplication);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final TripApplication tripApplication, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tripApplication);
		else
			try {
				this.tripApplicationService.save(tripApplication);
				result = new ModelAndView("redirect:/tripApplication/manager/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tripApplication, "tripApplication.commit.error");
			}

		return result;

	}
	protected ModelAndView createEditModelAndView(final TripApplication tripApplication) {
		ModelAndView result;
		result = this.createEditModelAndView(tripApplication, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final TripApplication tripApplication, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("tripApplication/edit");
		result.addObject("tripApplication", tripApplication);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
