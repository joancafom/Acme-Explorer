
package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.TripService;
import controllers.AbstractController;
import domain.Audit;
import domain.Trip;

@Controller
@RequestMapping("/audit/manager")
public class AuditManagerController extends AbstractController {

	/* Services */
	@Autowired
	private AuditService	auditService;

	@Autowired
	private TripService		tripService;


	public AuditManagerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tripId) {
		ModelAndView result;
		Collection<Audit> audits;

		Assert.notNull(tripId);
		final Trip trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);

		audits = this.auditService.findByTrip(trip);

		result = new ModelAndView("audit/list");
		result.addObject("audits", audits);
		result.addObject("actorWS", "manager/");
		result.addObject("requestURI", "audit/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int auditId) {
		ModelAndView result;
		Audit audit;

		Assert.notNull(auditId);
		audit = this.auditService.findOne(auditId);
		Assert.notNull(audit);

		result = new ModelAndView("audit/display");
		result.addObject("audit", audit);

		return result;
	}

}
