
package controllers.sponsor;

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
import services.SponsorService;
import services.SponsorshipService;
import services.TripService;
import controllers.AbstractController;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Trip;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorshipSponsorController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private TripService			tripService;


	// Constructors ---------------------------

	public SponsorshipSponsorController() {
		super();
	}

	// Display --------------------------------

	// Listing --------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		final Collection<Sponsorship> sponsorships;

		sponsorships = this.sponsorshipService.findByCurrentSponsor();

		res = new ModelAndView("sponsorship/list");
		res.addObject("sponsorships", sponsorships);
		res.addObject("requestURI", "sponsorship/sponsor/list.do");
		res.addObject("tripURI", "trip/sponsor/display.do?tripId=");

		return res;
	}

	// Creation -------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		final Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.create();
		res = this.createEditModelAndView(sponsorship);

		return res;
	}

	// Edition --------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		ModelAndView res;
		final Sponsorship sponsorship;
		final Sponsor sponsor;

		final UserAccount userAccount = LoginService.getPrincipal();
		sponsor = this.sponsorService.findByUserAccount(userAccount);

		sponsorship = this.sponsorshipService.findOne(sponsorshipId);

		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getSponsor().equals(sponsor));

		res = this.createEditModelAndView(sponsorship);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(sponsorship);
		else
			try {
				this.sponsorshipService.save(sponsorship);
				res = new ModelAndView("redirect:/sponsorship/sponsor/list.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
			}

		return res;
	}
	// Ancillary methods ----------------------

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		ModelAndView res;

		res = this.createEditModelAndView(sponsorship, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String messageCode) {
		ModelAndView res;
		Collection<Trip> trips;

		trips = this.tripService.findAllPublished();

		res = new ModelAndView("sponsorship/edit");
		res.addObject("sponsorship", sponsorship);
		res.addObject("trips", trips);
		res.addObject("messageCode", messageCode);

		return res;
	}
}
