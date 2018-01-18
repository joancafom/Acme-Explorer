
package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CurriculumService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.Manager;
import domain.Ranger;

@Controller
@RequestMapping("/curriculum/manager")
public class CurriculumManagerController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private ManagerService		managerService;


	// Constructors ---------------------------

	public CurriculumManagerController() {
		super();
	}

	// Display --------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int curriculumId) {
		final ModelAndView res;
		final Curriculum curriculum;
		Ranger ranger;
		final Manager manager;

		curriculum = this.curriculumService.findOne(curriculumId);
		ranger = curriculum.getRanger();
		manager = this.managerService.findByUserAccount(LoginService.getPrincipal());

		Assert.isTrue(this.managerService.rangerHasManagerTrips(ranger, manager));

		res = new ModelAndView("curriculum/display");
		res.addObject("curriculum", curriculum);
		res.addObject("RequestURI", "curriculum/manager/display.do?curriculumId=" + curriculum.getId());
		res.addObject("ownCurriculum", false);

		return res;
	}
	// Creation -------------------------------

	// Edition --------------------------------

	// Ancillary methods ----------------------

}
