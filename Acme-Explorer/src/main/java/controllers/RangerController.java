
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.RangerService;
import services.UserAccountService;
import domain.Ranger;

@Controller
@RequestMapping("/ranger")
public class RangerController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private RangerService		rangerService;


	// Constructors ---------------------------

	public RangerController() {
		super();
	}

	// Display --------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int rangerId) {
		final ModelAndView res;
		Ranger ranger;

		ranger = this.rangerService.findOne(rangerId);

		res = new ModelAndView("ranger/display");
		res.addObject("ranger", ranger);
		res.addObject("socialIDRequestURI", "ranger/display.do?rangerId=" + ranger.getId());

		return res;
	}

	// Creation -------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		final ModelAndView res;
		final Ranger ranger;
		final UserAccount userAccount;

		userAccount = this.userAccountService.create();
		ranger = this.rangerService.create(userAccount);

		res = this.createEditModelAndView(ranger);

		return res;

	}

	// Edition --------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Ranger ranger, final BindingResult bindingResult) {

		ModelAndView res;

		if (bindingResult.hasErrors())
			res = this.createEditModelAndView(ranger);
		else {

			final String hashedPassword;
			final String oldPassword = ranger.getUserAccount().getPassword();

			try {

				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				hashedPassword = encoder.encodePassword(ranger.getUserAccount().getPassword(), null);

				ranger.getUserAccount().setPassword(hashedPassword);

				this.rangerService.save(ranger);
				res = new ModelAndView("redirect:/");

			} catch (final Throwable oops) {
				ranger.getUserAccount().setPassword(oldPassword);
				res = this.createEditModelAndView(ranger, "actor.commit.error");
			}
		}

		return res;

	}

	// Ancillary methods ----------------------

	protected ModelAndView createEditModelAndView(final Ranger ranger) {
		return this.createEditModelAndView(ranger, null);
	}

	protected ModelAndView createEditModelAndView(final Ranger ranger, final String messageCode) {
		final ModelAndView res;

		res = new ModelAndView("ranger/edit");
		res.addObject("actorClassName", "ranger");
		res.addObject("ranger", ranger);
		res.addObject("requestURI", "ranger/create.do");
		res.addObject("actionURI", "ranger/edit.do");

		res.addObject("messageCode", messageCode);

		return res;
	}
}
