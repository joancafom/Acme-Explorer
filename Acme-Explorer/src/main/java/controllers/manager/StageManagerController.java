
package controllers.manager;

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
import services.ManagerService;
import services.StageService;
import services.TripService;
import controllers.AbstractController;
import domain.Manager;
import domain.Stage;
import domain.Trip;

@Controller
@RequestMapping("/stage/manager")
public class StageManagerController extends AbstractController {

	//Services
	@Autowired
	private StageService	stageService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private ManagerService	managerService;


	//Creation
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView res;

		final Trip trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);

		final Stage stage = this.stageService.create(trip);

		res = this.createEditModelAndView(stage);

		return res;
	}

	//Edit
	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam final int stageId) {
		final ModelAndView res;

		Stage stage;
		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);
		Assert.notNull(manager);

		stage = this.stageService.findOne(stageId);

		Assert.notNull(stage);
		Assert.isTrue(manager.equals(stage.getTrip().getManager()));

		res = this.createEditModelAndView(stage);

		return res;
	}
	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Stage stage, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(stage);
		else
			try {

				this.stageService.save(stage);
				res = new ModelAndView("redirect:/trip/manager/display.do?tripId=" + stage.getTrip().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(stage, "stage.commit.error");
			}

		return res;
	}

	//Delete
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Stage stage, final BindingResult binding) {
		ModelAndView res;

		try {
			this.stageService.delete(stage);
			res = new ModelAndView("redirect:/trip/manager/display.do?tripId=" + stage.getTrip().getId());
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(stage, "stage.commit.error");
		}

		return res;
	}
	//Ancillary Methods
	public ModelAndView createEditModelAndView(final Stage stage) {
		return this.createEditModelAndView(stage, null);
	}

	public ModelAndView createEditModelAndView(final Stage stage, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("stage/edit");
		res.addObject("stage", stage);

		res.addObject("message", messageCode);

		return res;
	}
}
