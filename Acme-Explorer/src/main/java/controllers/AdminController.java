/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;
import services.LegalTextService;
import domain.LegalText;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {

	@Autowired
	private AdminService		adminService;

	@Autowired
	private LegalTextService	legalTextService;


	// Display Dashboard ---------------------------------------------------------------		

	@RequestMapping("/display-dashboard")
	public ModelAndView dashboard() {
		ModelAndView res;

		final Collection<LegalText> legalTexts = this.legalTextService.findAll();
		final Map<Integer, Integer> references = new HashMap<Integer, Integer>();

		for (final LegalText l : legalTexts)
			references.put(new Integer(l.getId()), this.adminService.getNumberReferences(l));

		res = new ModelAndView("admin/display-dashboard");

		res.addObject("tripApplicationsPerTripStatistics", this.adminService.getApplicationsPerTripStatistics().iterator().next());
		res.addObject("tripsPerManagerStatistics", this.adminService.getTripsPerManagerStatistics().iterator().next());
		res.addObject("tripsPriceStatistics", this.adminService.getTripPricesStatistics().iterator().next());
		res.addObject("tripsPerRangerStatistics", this.adminService.getTripsPerRangerStatistics().iterator().next());
		res.addObject("pendingRatio", this.adminService.getPendingApplicationRatio());
		res.addObject("dueRatio", this.adminService.getDueApplicationRatio());
		res.addObject("acceptedRatio", this.adminService.getAcceptedApplicationRatio());
		res.addObject("cancelledRatio", this.adminService.getCancelledApplicationRatio());
		res.addObject("cancelledVsOrganisedRatio", this.adminService.getCancelledVsOrganisedTripsRatio());
		res.addObject("tripsMoreApplications", this.adminService.getTripsMoreApplicationsOrdered());
		res.addObject("references", references);
		res.addObject("legalTexts", legalTexts);
		res.addObject("notesPerTripStatistics", this.adminService.getNotesPerTripStatistics().iterator().next());
		res.addObject("auditsPerTripStatistics", this.adminService.getAuditPerTripStatistics().iterator().next());
		res.addObject("tripsWithAuditRatio", this.adminService.getTripsWithAuditRatio());
		res.addObject("rangersCurriculumRatio", this.adminService.getRangersWithCurriculumRatio());
		res.addObject("rangersERRatio", this.adminService.getRangersWithERRatio());
		res.addObject("suspiciousManagersRatio", this.adminService.getSuspiciousManagersRatio());
		res.addObject("suspiciousRangersRatio", this.adminService.getSuspiciousRangersRatio());

		return res;
	}
}
