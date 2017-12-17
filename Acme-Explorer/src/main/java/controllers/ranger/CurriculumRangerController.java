
package controllers.ranger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import controllers.AbstractController;
import domain.Curriculum;

@Controller
@RequestMapping("/curriculum/ranger")
public class CurriculumRangerController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private CurriculumService	curriculumService;


	// Constructors ---------------------------

	public CurriculumRangerController() {
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
		res.addObject("RequestURI", "curriculum/ranger/display.do?curriculumId=" + curriculum.getId());

		return res;
	}

	// Creation -------------------------------

	// Edition --------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int curriculumId) {
		ModelAndView res;
		final Curriculum curriculum;

		curriculum = this.curriculumService.findOne(curriculumId);
		Assert.notNull(curriculum);

		res = this.createEditModelAndView(curriculum);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Curriculum curriculum, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(curriculum);
		else
			try {
				this.curriculumService.delete(curriculum);
				res = new ModelAndView("redirect:/index.jsp");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(curriculum, "curriculum.commit.error");
			}

		return res;
	}

	// Ancillary methods ----------------------

	protected ModelAndView createEditModelAndView(final Curriculum curriculum) {
		ModelAndView res;

		res = this.createEditModelAndView(curriculum, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final Curriculum curriculum, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("curriculum/edit");
		res.addObject("curriculum", curriculum);

		res.addObject("message", messageCode);

		return res;
	}

}
