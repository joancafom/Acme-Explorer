
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

import security.LoginService;
import security.UserAccount;
import services.CurriculumService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.Ranger;

@Controller
@RequestMapping("/curriculum/ranger")
public class CurriculumRangerController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private RangerService		rangerService;


	// Constructors ---------------------------

	public CurriculumRangerController() {
		super();
	}

	// Display --------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) Integer curriculumId) {
		final ModelAndView res;
		final Curriculum curriculum;
		Ranger ranger;
		Boolean b = false;

		final UserAccount userAccount = LoginService.getPrincipal();
		ranger = this.rangerService.findByUserAccount(userAccount);

		if (curriculumId == null)
			curriculumId = ranger.getCurriculum().getId();

		curriculum = this.curriculumService.findOne(curriculumId);

		Assert.isTrue(curriculum.getRanger().equals(ranger) || this.rangerService.hasPublicatedTrips(curriculum.getRanger()));

		if (ranger.equals(curriculum.getRanger()))
			b = true;

		res = new ModelAndView("curriculum/display");
		res.addObject("curriculum", curriculum);
		res.addObject("RequestURI", "curriculum/ranger/display.do?curriculumId=" + curriculum.getId());
		res.addObject("ownCurriculum", b);

		return res;
	}

	// Creation -------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		final Curriculum curriculum;

		curriculum = this.curriculumService.create();

		res = this.createEditModelAndView(curriculum);

		return res;
	}

	// Edition --------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int curriculumId) {
		ModelAndView res;
		final Curriculum curriculum;

		curriculum = this.curriculumService.findOne(curriculumId);
		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getRanger().getUserAccount().equals(LoginService.getPrincipal()));

		res = this.createEditModelAndView(curriculum);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Curriculum curriculum, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(curriculum);
		else
			try {
				this.curriculumService.save(curriculum);
				res = new ModelAndView("redirect:/curriculum/ranger/display.do?curriculumId=" + curriculum.getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(curriculum, "personalRecord.commit.error");
			}

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
				res = new ModelAndView("redirect:/actor/ranger/display.do");
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

		res.addObject("messageCode", messageCode);

		return res;
	}

}
