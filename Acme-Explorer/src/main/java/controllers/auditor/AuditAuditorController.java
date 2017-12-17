
package controllers.auditor;

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
import services.AuditService;
import services.AuditorService;
import services.TripService;
import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;
import domain.Trip;

@Controller
@RequestMapping("/audit/auditor")
public class AuditAuditorController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private AuditService	auditService;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private TripService		tripService;


	// Display --------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int auditId) {
		final ModelAndView res;
		Audit audit;

		audit = this.auditService.findOne(auditId);
		Assert.notNull(audit);

		res = new ModelAndView("audit/display");
		res.addObject("audit", audit);
		res.addObject("actorWS", "auditor/");

		return res;
	}

	// Listing --------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Audit> audits;

		audits = this.auditService.findByCurrentAuditor();

		res = new ModelAndView("audit/list");
		res.addObject("audits", audits);
		res.addObject("actorWS", "auditor/");
		res.addObject("requestURI", "audit/auditor/list.do");

		return res;
	}
	// Creation -------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView res;
		Trip trip;

		trip = this.tripService.findOne(tripId);
		final Audit audit = this.auditService.create(trip);
		res = this.createEditModelAndView(audit);

		return res;
	}

	// Edition --------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditId) {
		ModelAndView res;
		final Audit audit;

		audit = this.auditService.findOne(auditId);
		Assert.notNull(audit);

		final UserAccount userAccount = LoginService.getPrincipal();
		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);

		Assert.notNull(auditor);
		Assert.isTrue(auditor.equals(audit.getAuditor()));
		Assert.isTrue(!audit.getIsFinal());

		res = this.createEditModelAndView(audit);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Audit audit, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(audit);
		else
			try {
				this.auditService.save(audit);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(audit, "audit.commit.error");
			}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Audit audit, final BindingResult binding) {
		ModelAndView res;

		try {
			this.auditService.delete(audit);
			res = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(audit, "audit.commit.error");
		}

		return res;
	}

	// Ancillary methods ----------------------

	protected ModelAndView createEditModelAndView(final Audit audit) {
		ModelAndView res;

		res = this.createEditModelAndView(audit, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final Audit audit, final String messageCode) {
		ModelAndView res;
		final Collection<Trip> trips;

		final UserAccount userAccount = LoginService.getPrincipal();
		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);

		Assert.notNull(auditor);

		trips = this.tripService.findAll();
		trips.removeAll(this.tripService.findAuditorAuditedTrips(auditor));
		trips.add(audit.getTrip());

		res = new ModelAndView("audit/edit");
		res.addObject("audit", audit);
		res.addObject("trips", trips);

		res.addObject("message", messageCode);

		return res;
	}
}
