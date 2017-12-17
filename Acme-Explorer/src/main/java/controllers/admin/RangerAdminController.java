
package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.RangerService;
import services.UserAccountService;
import controllers.AbstractController;
import domain.Ranger;

@Controller
@RequestMapping("/ranger/admin")
public class RangerAdminController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private RangerService		rangerService;


	// Constructors ---------------------------

	public RangerAdminController() {
		super();
	}

	// Display --------------------------------

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
		res.addObject("requestURI", "ranger/admin/create.do");
		res.addObject("actionURI", "ranger/admin/edit.do");

		res.addObject("message", messageCode);

		return res;
	}
}
