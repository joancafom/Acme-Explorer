
package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RangerService;
import controllers.AbstractController;
import domain.Ranger;

@Controller
@RequestMapping("/ranger/manager")
public class RangerManagerController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private RangerService	rangerService;


	// Constructors ---------------------------

	public RangerManagerController() {
		super();
	}

	// Display --------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int rangerId) {
		final ModelAndView res;
		Ranger ranger;

		ranger = this.rangerService.findOne(rangerId);

		res = new ModelAndView("ranger/display");
		res.addObject("actor", ranger);
		res.addObject("curriculumURI", "curriculum/manager/display.do?curriculumId=" + ranger.getCurriculum().getId());
		res.addObject("ownProfile", false);

		return res;
	}

	// Creation -------------------------------

	// Edition --------------------------------

	// Ancillary methods ----------------------

}
