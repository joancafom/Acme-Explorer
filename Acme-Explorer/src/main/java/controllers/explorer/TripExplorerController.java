
package controllers.explorer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.CategoryService;
import services.ExplorerService;
import services.FinderService;
import services.TripApplicationService;
import services.TripService;
import controllers.AbstractController;
import domain.Category;
import domain.Explorer;
import domain.Finder;
import domain.Sponsorship;
import domain.Trip;

@Controller
@RequestMapping("/trip/explorer")
public class TripExplorerController extends AbstractController {

	//Services
	@Autowired
	private TripService				tripService;

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private TripApplicationService	tripApplicationService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String keyword, @RequestParam(required = false) final Integer categoryId, @RequestParam(required = false) final Integer finderId) {

		final ModelAndView res;
		Collection<Trip> trips = null;
		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);
		Assert.notNull(explorer);

		final Map<String, Boolean> canCreateTA = new HashMap<String, Boolean>();

		if (keyword == null && categoryId == null && finderId == null)
			trips = this.tripService.findAllPublished();

		else if (keyword == null && categoryId != null && finderId == null) {
			final Category category = this.categoryService.findOne(categoryId);
			trips = this.tripService.findByCategoryPublished(category);

		} else if (keyword != null && categoryId == null && finderId == null)
			trips = this.tripService.findByKeyWordPublished(keyword);

		else if (keyword == null && categoryId == null && finderId != null) {
			final Finder finder = this.finderService.findOne(finderId);
			trips = this.tripService.findByFinderPublished(finder);
		}

		for (final Trip t : trips)
			if (this.tripApplicationService.findByExplorerAndTrip(explorer, t) != null)
				canCreateTA.put(t.getTicker(), false);
			else
				canCreateTA.put(t.getTicker(), true);

		res = new ModelAndView("trip/list");
		res.addObject("trips", trips);
		res.addObject("requestURI", "/trip/explorer/list.do");
		res.addObject("actorWS", "explorer/");
		res.addObject("canCreateTA", canCreateTA);

		return res;
	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tripId) {
		final ModelAndView res;
		Trip trip;
		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);
		Assert.notNull(explorer);

		trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);
		Assert.isTrue(trip.getPublicationDate().before(new Date()));

		final List<Sponsorship> sponsorships = new ArrayList<Sponsorship>(trip.getSponsorships());
		Collections.shuffle(sponsorships);
		Sponsorship sponsorship = null;
		if (sponsorships.isEmpty() == false)
			sponsorship = sponsorships.get(0);

		final Boolean canCreateTA = this.tripApplicationService.findByExplorerAndTrip(explorer, trip) == null ? true : false;

		res = new ModelAndView("trip/display");
		res.addObject("trip", trip);
		res.addObject("sponsorship", sponsorship);
		res.addObject("rangerURI", "ranger/explorer/display.do?tripId=" + tripId);
		res.addObject("requestURI", "trip/explorer/display.do?tripId=" + tripId);
		res.addObject("canCreateTA", canCreateTA);
		res.addObject("myTrip", false);

		return res;

	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		final ModelAndView res;

		res = new ModelAndView("trip/search");
		res.addObject("actorWS", "explorer/");

		return res;

	}

}
