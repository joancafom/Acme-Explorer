
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
import services.EndorserRecordService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.EndorserRecord;

@Controller
@RequestMapping("/endorserRecord/ranger")
public class EndorserRecordRangerController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private EndorserRecordService	endorserRecordService;

	@Autowired
	private CurriculumService		curriculumService;


	// Constructors ---------------------------

	public EndorserRecordRangerController() {
		super();
	}

	// Display --------------------------------

	// Listing --------------------------------

	// Creation -------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView res;
		final Curriculum curriculum;
		final EndorserRecord endorserRecord;

		curriculum = this.curriculumService.findOne(curriculumId);
		endorserRecord = this.endorserRecordService.create(curriculum);
		res = this.createEditModelAndView(endorserRecord);

		return res;
	}

	// Edition --------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorserRecordId) {
		ModelAndView res;
		final EndorserRecord endorserRecord;

		endorserRecord = this.endorserRecordService.findOne(endorserRecordId);
		Assert.notNull(endorserRecord);

		res = this.createEditModelAndView(endorserRecord);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EndorserRecord endorserRecord, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(endorserRecord);
		else
			try {
				this.endorserRecordService.save(endorserRecord);
				res = new ModelAndView("redirect:/curriculum/ranger/display.do?curriculumId=" + endorserRecord.getCurriculum().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(endorserRecord, "endorserRecord.commit.error");
			}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final EndorserRecord endorserRecord, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(endorserRecord);
		else
			try {
				this.endorserRecordService.delete(endorserRecord);
				res = new ModelAndView("redirect:/curriculum/ranger/display.do?curriculumId=" + endorserRecord.getCurriculum().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(endorserRecord, "endorserRecord.commit.error");
			}

		return res;
	}

	// Ancillary methods ----------------------

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord) {
		ModelAndView res;

		res = this.createEditModelAndView(endorserRecord, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("endorserRecord/edit");
		res.addObject("endorserRecord", endorserRecord);

		return res;
	}

}
