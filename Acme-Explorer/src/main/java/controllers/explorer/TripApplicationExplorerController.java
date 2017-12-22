
package controllers.explorer;

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

import security.LoginService;
import security.UserAccount;
import services.ExplorerService;
import services.TripApplicationService;
import services.TripService;
import controllers.AbstractController;
import domain.ApplicationStatus;
import domain.Explorer;
import domain.Trip;
import domain.TripApplication;

@Controller
@RequestMapping("/tripApplication/explorer")
public class TripApplicationExplorerController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private TripApplicationService	tripApplicationService;

	@Autowired
	private TripService				tripService;

	@Autowired
	private ExplorerService			explorerService;


	// Constructors ---------------------------

	public TripApplicationExplorerController() {
		super();
	}

	// Display --------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tripApplicationId) {
		final ModelAndView res;
		TripApplication tripApplication;

		tripApplication = this.tripApplicationService.findOne(tripApplicationId);

		res = new ModelAndView("tripApplication/display");
		res.addObject("tripApplication", tripApplication);

		return res;
	}

	// Listing --------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<TripApplication> tripApplications;

		tripApplications = this.tripApplicationService.findByCurrentExplorer();

		res = new ModelAndView("tripApplication/list");
		res.addObject("tripApplications", tripApplications);
		res.addObject("requestURI", "tripApplication/explorer/list.do");

		return res;
	}

	// Creation -------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView res;
		Trip trip;
		TripApplication tripApplication;

		trip = this.tripService.findOne(tripId);
		tripApplication = this.tripApplicationService.create(trip);
		res = this.createEditModelAndView(tripApplication);

		return res;
	}

	// Edition --------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tripApplicationId) {
		ModelAndView res;
		TripApplication tripApplication;

		tripApplication = this.tripApplicationService.findOne(tripApplicationId);
		Assert.notNull(tripApplication);
		Assert.isTrue(tripApplication.getStatus().equals(ApplicationStatus.DUE) || tripApplication.getStatus().equals(ApplicationStatus.ACCEPTED));

		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);

		if (explorer != null)
			Assert.isTrue(tripApplication.getExplorer().equals(explorer));

		res = this.createEditModelAndView(tripApplication);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final TripApplication tripApplication, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(tripApplication);
		else
			try {
				if (tripApplication.getCreditCard() != null)
					this.tripApplicationService.changeApplicationStatus(tripApplication, ApplicationStatus.ACCEPTED);

				this.tripApplicationService.save(tripApplication);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(tripApplication, "tripApplication.commit.error");
			}

		return res;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int tripApplicationId) {
		ModelAndView res;
		final TripApplication tripApplication;
		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);

		Assert.notNull(explorer);

		tripApplication = this.tripApplicationService.findOne(tripApplicationId);
		Assert.notNull(tripApplication);
		Assert.isTrue(explorer.equals(tripApplication.getExplorer()));

		res = new ModelAndView("tripApplication/cancel");
		res.addObject("tripApplication", tripApplication);

		return res;

	}

	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public ModelAndView cancelSave(@Valid final TripApplication tripApplication, final BindingResult binding) {
		ModelAndView res;

		res = new ModelAndView("tripApplication/cancel");

		if (binding.hasErrors())
			res.addObject("tripApplication", tripApplication);
		else
			try {

				this.tripApplicationService.cancel(tripApplication);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				res.addObject("messageCode", "tripApplication.commit.error");
				res.addObject("tripApplication", tripApplication);
			}
		return res;

	}

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
		res.addObject("messageCode", messageCode);

		return res;
	}

}
