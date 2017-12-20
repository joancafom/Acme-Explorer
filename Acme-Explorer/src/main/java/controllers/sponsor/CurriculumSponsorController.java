
package controllers.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.Ranger;

@Controller
@RequestMapping("/curriculum/sponsor")
public class CurriculumSponsorController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private RangerService		rangerService;


	// Constructors ---------------------------

	public CurriculumSponsorController() {
		super();
	}

	// Display --------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int curriculumId) {
		final ModelAndView res;
		final Curriculum curriculum;
		Ranger ranger;

		curriculum = this.curriculumService.findOne(curriculumId);
		ranger = curriculum.getRanger();

		Assert.isTrue(this.rangerService.hasPublicatedTrips(ranger));

		res = new ModelAndView("curriculum/display");
		res.addObject("curriculum", curriculum);
		res.addObject("RequestURI", "curriculum/sponsor/display.do?curriculumId=" + curriculum.getId());
		res.addObject("ownCurriculum", false);

		return res;
	}
	// Creation -------------------------------

	// Edition --------------------------------

	// Ancillary methods ----------------------

}
