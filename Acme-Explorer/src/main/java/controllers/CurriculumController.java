
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import domain.Curriculum;

@Controller
@RequestMapping("/curriculum")
public class CurriculumController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private CurriculumService	curriculumService;


	// Constructors ---------------------------

	public CurriculumController() {
		super();
	}

	// Display --------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int curriculumId) {
		final ModelAndView res;
		final Curriculum curriculum;

		curriculum = this.curriculumService.findOne(curriculumId);

		res = new ModelAndView("curriculum/display");
		res.addObject("curriculum", curriculum);
		res.addObject("RequestURI", "curriculum/display.do?curriculumId=" + curriculum.getId());

		return res;
	}

	// Creation -------------------------------

	// Edition --------------------------------

	// Ancillary methods ----------------------

}
