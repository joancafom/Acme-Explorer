
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
import services.MiscellaneousRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.MiscellaneousRecord;
import domain.Ranger;

@Controller
@RequestMapping("/miscellaneousRecord/ranger")
public class MiscellaneousRecordRangerController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private RangerService				rangerService;


	// Constructors ---------------------------

	public MiscellaneousRecordRangerController() {
		super();
	}

	// Display --------------------------------

	// Listing --------------------------------

	// Creation -------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView res;
		final Curriculum curriculum;
		final MiscellaneousRecord miscellaneousRecord;

		curriculum = this.curriculumService.findOne(curriculumId);
		miscellaneousRecord = this.miscellaneousRecordService.create(curriculum);
		res = this.createEditModelAndView(miscellaneousRecord);

		return res;
	}

	// Edition --------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousRecordId) {
		ModelAndView res;
		final MiscellaneousRecord miscellaneousRecord;
		Ranger ranger;

		final UserAccount userAccount = LoginService.getPrincipal();
		ranger = this.rangerService.findByUserAccount(userAccount);

		miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);

		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getCurriculum().getRanger().equals(ranger));

		res = this.createEditModelAndView(miscellaneousRecord);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(miscellaneousRecord);
		else
			try {
				this.miscellaneousRecordService.save(miscellaneousRecord);
				res = new ModelAndView("redirect:/curriculum/ranger/display.do?curriculumId=" + miscellaneousRecord.getCurriculum().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");
			}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(miscellaneousRecord);
		else
			try {
				this.miscellaneousRecordService.delete(miscellaneousRecord);
				res = new ModelAndView("redirect:/curriculum/ranger/display.do?curriculumId=" + miscellaneousRecord.getCurriculum().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");
			}

		return res;
	}

	// Ancillary methods ----------------------

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord) {
		ModelAndView res;

		res = this.createEditModelAndView(miscellaneousRecord, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("miscellaneousRecord/edit");
		res.addObject("miscellaneousRecord", miscellaneousRecord);
		res.addObject("messageCode", messageCode);

		return res;
	}
}
