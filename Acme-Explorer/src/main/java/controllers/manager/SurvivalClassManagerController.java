
package controllers.manager;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ManagerService;
import services.SurvivalClassService;
import services.TripService;
import controllers.AbstractController;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;

@Controller
@RequestMapping("/survivalClass/manager")
public class SurvivalClassManagerController extends AbstractController {

	/* Services */
	@Autowired
	private SurvivalClassService	survivalClassService;
	@Autowired
	private TripService				tripService;
	@Autowired
	private ManagerService			managerService;


	public SurvivalClassManagerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer tripId) {
		final ModelAndView result;
		Collection<SurvivalClass> survivalClasses;
		final Manager manager = this.managerService.findByUserAccount(LoginService.getPrincipal());

		if (tripId == null)
			/* Manager's survivalClasses */
			survivalClasses = this.survivalClassService.findByCurrentManager();
		else {
			/* SurvivalClasses of a trip */
			final Trip trip = this.tripService.findOne(tripId);
			Assert.notNull(trip);
			if (manager.getTrips().contains(trip))
				survivalClasses = this.survivalClassService.findAllByTrip(trip);
			else if (!manager.getTrips().contains(trip) && trip.getPublicationDate().before(new Date()))
				survivalClasses = this.survivalClassService.findAllByTrip(trip);
			else
				survivalClasses = new HashSet<SurvivalClass>();
		}

		result = new ModelAndView("survivalClass/list");
		result.addObject("survivalClasses", survivalClasses);
		result.addObject("requestURI", "survivalClass/manager/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SurvivalClass survivalClass;

		survivalClass = this.survivalClassService.create();
		Assert.notNull(survivalClass);

		result = this.createEditModelAndView(survivalClass);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SurvivalClass survivalClass, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(survivalClass);
		else
			try {
				this.survivalClassService.save(survivalClass);
				result = new ModelAndView("redirect:/survivalClass/manager/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(survivalClass, "survivalClass.commit.error");
			}

		return result;

	}

	protected ModelAndView createEditModelAndView(final SurvivalClass survivalClass) {
		ModelAndView result;
		result = this.createEditModelAndView(survivalClass, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final SurvivalClass survivalClass, final String messageCode) {
		ModelAndView result;
		final Collection<Trip> trips = this.tripService.findAllManagedBy(this.managerService.findByUserAccount(LoginService.getPrincipal()));
		result = new ModelAndView("survivalClass/edit");
		result.addObject("survivalClass", survivalClass);
		result.addObject("trips", trips);

		return result;
	}

}
