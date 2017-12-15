
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

import services.ProfessionalRecordService;
import controllers.AbstractController;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/professionalRecord/ranger")
public class ProfessionalRecordRangerController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private ProfessionalRecordService	professionalRecordService;


	// Constructors ---------------------------

	public ProfessionalRecordRangerController() {
		super();
	}

	// Display --------------------------------

	// Listing --------------------------------

	// Creation -------------------------------

	// Edition --------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int professionalRecordId) {
		ModelAndView res;
		final ProfessionalRecord professionalRecord;

		professionalRecord = this.professionalRecordService.findOne(professionalRecordId);
		Assert.notNull(professionalRecord);

		res = this.createEditModelAndView(professionalRecord);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProfessionalRecord professionalRecord, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(professionalRecord);
		else
			try {
				this.professionalRecordService.save(professionalRecord);
				res = new ModelAndView("redirect:/curriculum/ranger/display.do?curriculumId=" + professionalRecord.getCurriculum().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(professionalRecord, "professionalRecord.commit.error");
			}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final ProfessionalRecord professionalRecord, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(professionalRecord);
		else
			try {
				this.professionalRecordService.delete(professionalRecord);
				res = new ModelAndView("redirect:/curriculum/ranger/display.do?curriculumId=" + professionalRecord.getCurriculum().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(professionalRecord, "professionalRecord.commit.error");
			}

		return res;
	}

	// Ancillary methods ----------------------

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord) {
		ModelAndView res;

		res = this.createEditModelAndView(professionalRecord, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("professionalRecord/edit");
		res.addObject("professionalRecord", professionalRecord);

		return res;
	}

}
