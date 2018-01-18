
package controllers.explorer;

import java.util.Collection;
import java.util.Date;

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
import services.ExplorerService;
import services.SurvivalClassService;
import services.TripService;
import controllers.AbstractController;
import domain.Explorer;
import domain.SurvivalClass;
import domain.Trip;

@Controller
@RequestMapping("/survivalClass/explorer")
public class SurvivalClassExplorerController extends AbstractController {

	/* Services */
	@Autowired
	private SurvivalClassService	survivalClassService;
	@Autowired
	private TripService				tripService;
	@Autowired
	private ExplorerService			explorerService;


	public SurvivalClassExplorerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer tripId) {
		final ModelAndView result;
		final Collection<SurvivalClass> survivalClasses;
		final Explorer explorer = this.explorerService.findByUserAccount(LoginService.getPrincipal());
		if (tripId == null)
			survivalClasses = explorer.getSurvivalClasses();
		else {
			final Trip trip = this.tripService.findOne(tripId);
			Assert.notNull(trip);
			Assert.isTrue(trip.getPublicationDate().before(new Date()));
			survivalClasses = trip.getSurvivalClasses();

		}

		result = new ModelAndView("survivalClass/list");
		result.addObject("survivalClasses", survivalClasses);
		result.addObject("requestURI", "survivalClass/explorer/list.do");
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int survivalClassId) {
		ModelAndView result;
		Boolean enrollable = true;
		Boolean alreadyEnrolled = false;
		final Explorer explorer = this.explorerService.findByUserAccount(LoginService.getPrincipal());

		final SurvivalClass survivalClass = this.survivalClassService.findOne(survivalClassId);
		Assert.notNull(survivalClass);

		if (!this.explorerService.findAcceptedTripsByExplorer().contains(survivalClass.getTrip()))
			enrollable = false;

		if (explorer.getSurvivalClasses().contains(survivalClass))
			alreadyEnrolled = true;

		result = new ModelAndView("survivalClass/display");
		result.addObject("survivalClass", survivalClass);
		result.addObject("isEnrollable", enrollable);
		result.addObject("alreadyEnrolled", alreadyEnrolled);

		return result;
	}

	@RequestMapping(value = "/enroll", method = RequestMethod.GET)
	public ModelAndView enroll(@RequestParam final int survivalClassId) {
		final ModelAndView result;

		final SurvivalClass survivalClass = this.survivalClassService.findOne(survivalClassId);
		Assert.notNull(survivalClass);

		result = this.createEditModelAndView(survivalClass);

		return result;
	}

	private ModelAndView createEditModelAndView(final SurvivalClass survivalClass) {
		ModelAndView result;
		result = this.createEditModelAndView(survivalClass, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final SurvivalClass survivalClass, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("survivalClass/edit");
		result.addObject("survivalClass", survivalClass);
		result.addObject("actor", "explorer");
		result.addObject("messageCode", messageCode);
		result.addObject("explorer", this.explorerService.findByUserAccount(LoginService.getPrincipal()));

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "enroll")
	public ModelAndView enrollSave(@Valid final SurvivalClass survivalClass, final BindingResult binding) {
		ModelAndView res;

		res = new ModelAndView("survivalClass/edit");

		if (binding.hasErrors())
			res.addObject("survivalClass", survivalClass);
		else
			try {

				this.survivalClassService.enroll(survivalClass);
				this.survivalClassService.save(survivalClass);
				res = new ModelAndView("redirect:/trip/explorer/display.do?tripId=" + survivalClass.getTrip().getId());
			} catch (final Throwable oops) {
				res.addObject("messageCode", "survivalClass.commit.error");
				res.addObject("survivalClass", survivalClass);

			}

		return res;

	}

}
