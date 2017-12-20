
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
import services.PersonalRecordService;
import services.RangerService;
import controllers.AbstractController;
import domain.PersonalRecord;
import domain.Ranger;

@Controller
@RequestMapping("/personalRecord/ranger")
public class PersonalRecordRangerController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private PersonalRecordService	personalRecordService;

	@Autowired
	private RangerService			rangerService;


	// Constructors ---------------------------

	public PersonalRecordRangerController() {
		super();
	}

	// Display --------------------------------

	// Listing --------------------------------

	// Creation -------------------------------

	// Edition --------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalRecordId) {
		ModelAndView res;
		final PersonalRecord personalRecord;
		Ranger ranger;

		final UserAccount userAccount = LoginService.getPrincipal();
		ranger = this.rangerService.findByUserAccount(userAccount);

		personalRecord = this.personalRecordService.findOne(personalRecordId);

		Assert.notNull(personalRecord);
		Assert.isTrue(personalRecord.getCurriculum().getRanger().equals(ranger));

		res = this.createEditModelAndView(personalRecord);

		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PersonalRecord personalRecord, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(personalRecord);
		else
			try {
				this.personalRecordService.save(personalRecord);
				res = new ModelAndView("redirect:/curriculum/ranger/display.do?curriculumId=" + personalRecord.getCurriculum().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(personalRecord, "personalRecord.commit.error");
			}

		return res;
	}

	// Ancillary methods ----------------------

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord) {
		ModelAndView res;

		res = this.createEditModelAndView(personalRecord, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("personalRecord/edit");
		res.addObject("personalRecord", personalRecord);
		res.addObject("messageCode", messageCode);

		return res;
	}

}
