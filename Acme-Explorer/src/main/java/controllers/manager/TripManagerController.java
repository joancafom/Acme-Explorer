
package controllers.manager;

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
import security.UserAccount;
import services.CategoryService;
import services.LegalTextService;
import services.ManagerService;
import services.RangerService;
import services.TagService;
import services.TripApplicationService;
import services.TripService;
import controllers.AbstractController;
import domain.ApplicationStatus;
import domain.Category;
import domain.LegalText;
import domain.Manager;
import domain.Ranger;
import domain.Sponsorship;
import domain.Tag;
import domain.Trip;
import domain.TripApplication;

@Controller
@RequestMapping("/trip/manager")
public class TripManagerController extends AbstractController {

	//Services
	@Autowired
	private TripService				tripService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private RangerService			rangerService;

	@Autowired
	private LegalTextService		legalTextService;

	@Autowired
	private TagService				tagService;

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private TripApplicationService	tripApplicationService;


	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String keyword, @RequestParam(required = false) final Integer categoryId, @RequestParam(required = false) final Boolean showAll) {
		final ModelAndView res;
		final Collection<Trip> trips;

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager current = this.managerService.findByUserAccount(userAccount);

		if (keyword == null && categoryId == null) {

			Assert.notNull(current);

			if (showAll == null || !showAll)
				trips = this.tripService.findAllManagedBy(current);
			else
				trips = this.tripService.findAllPublished();

		} else if (keyword != null)
			trips = this.tripService.findByKeyWordPublished(keyword);
		else {
			Assert.notNull(categoryId);
			final Category c = this.categoryService.findOne(categoryId);
			Assert.notNull(c);

			trips = this.tripService.findByCategoryPublished(c);
		}

		res = new ModelAndView("trip/list");
		res.addObject("trips", trips);
		res.addObject("requestURI", "/trip/manager/list.do");
		res.addObject("actorWS", "manager/");
		res.addObject("principalId", current.getId());

		return res;
	}
	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res;
		final Trip trip;

		trip = this.tripService.create();
		res = this.createEditModelAndView(trip);

		return res;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tripId) {
		ModelAndView res;
		Trip trip;
		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerService.findByUserAccount(userAccount);
		final Date now = new Date();
		Assert.notNull(manager);

		trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);
		Assert.isTrue(manager.equals(trip.getManager()));
		Assert.isTrue(now.before(trip.getEndingDate()));

		res = this.createEditModelAndView(trip);

		return res;

	}
	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Trip trip, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(trip);
		else
			try {

				if ("".equals(trip.getCancelationReason()))
					trip.setCancelationReason(null);
				else
					for (final TripApplication ta : trip.getTripApplications()) {
						ta.setRejectionReason("The trip was cancelled.");
						this.tripApplicationService.changeApplicationStatus(ta, ApplicationStatus.REJECTED);
					}

				this.tripService.save(trip);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(trip, "trip.commit.error");
			}

		return res;

	}
	//Delete
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Trip trip, final BindingResult binding) {
		ModelAndView res;

		try {
			this.tripService.delete(trip);
			res = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(trip, "trip.commit.error");
		}

		return res;

	}

	//TODO: Req 11.1 not mine
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
		res.addObject("rangerURI", "ranger/manager/display.do?tripId=" + tripId);

		return res;

	}

	//TODO: Req 11.1 not mine
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		final ModelAndView res;

		res = new ModelAndView("trip/search");
		res.addObject("actorWS", "manager/");

		return res;

	}

	//Ancillary Methods

	protected ModelAndView createEditModelAndView(final Trip trip) {
		return this.createEditModelAndView(trip, null);
	}

	protected ModelAndView createEditModelAndView(final Trip trip, final String messageCode) {
		final ModelAndView res;
		Collection<Ranger> rangers;
		Collection<LegalText> legalTexts;
		Collection<Tag> tags;
		Collection<Category> categories;

		rangers = this.rangerService.findAll();
		legalTexts = this.legalTextService.findAll();
		tags = this.tagService.findAll();
		categories = this.categoryService.findAll();

		res = new ModelAndView("trip/edit");
		res.addObject("trip", trip);
		res.addObject("rangers", rangers);
		res.addObject("legalTexts", legalTexts);
		res.addObject("tags", tags);
		res.addObject("categories", categories);

		res.addObject("messageCode", messageCode);

		return res;
	}
}
