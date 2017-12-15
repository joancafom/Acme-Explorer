
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

import services.EducationRecordService;
import controllers.AbstractController;
import domain.EducationRecord;

@Controller
@RequestMapping("/educationRecord/ranger")
public class EducationRecordRangerController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private EducationRecordService	educationRecordService;


	// Constructors ---------------------------

	public EducationRecordRangerController() {
		super();
	}

	// Display --------------------------------

	// Listing --------------------------------

	// Creation -------------------------------

	// Edition --------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationRecordId) {
		ModelAndView res;
		final EducationRecord educationRecord;

		educationRecord = this.educationRecordService.findOne(educationRecordId);
		Assert.notNull(educationRecord);

		res = this.createEditModelAndView(educationRecord);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationRecord educationRecord, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(educationRecord);
		else
			try {
				this.educationRecordService.save(educationRecord);
				res = new ModelAndView("redirect:/curriculum/ranger/display.do?curriculumId=" + educationRecord.getCurriculum().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(educationRecord, "educationRecord.commit.error");
			}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final EducationRecord educationRecord, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(educationRecord);
		else
			try {
				this.educationRecordService.delete(educationRecord);
				res = new ModelAndView("redirect:/curriculum/ranger/display.do?curriculumId=" + educationRecord.getCurriculum().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(educationRecord, "educationRecord.commit.error");
			}

		return res;
	}

	// Ancillary methods ----------------------

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord) {
		ModelAndView res;

		res = this.createEditModelAndView(educationRecord, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("educationRecord/edit");
		res.addObject("educationRecord", educationRecord);

		return res;
	}

}
