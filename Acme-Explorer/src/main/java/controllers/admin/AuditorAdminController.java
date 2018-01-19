
package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.AuditorService;
import services.UserAccountService;
import domain.Auditor;

@Controller
@RequestMapping("/auditor/admin")
public class AuditorAdminController {

	/* Services */
	@Autowired
	private AuditorService		auditorService;

	@Autowired
	private UserAccountService	userAccountService;


	public AuditorAdminController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		UserAccount userAccount;
		Auditor auditor;

		userAccount = this.userAccountService.create();
		auditor = this.auditorService.create(userAccount);

		result = this.createEditModelAndView(auditor);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Auditor auditor, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(auditor);
		else {

			final String hashedPassword;
			final String oldPassword = auditor.getUserAccount().getPassword();

			try {

				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				hashedPassword = encoder.encodePassword(auditor.getUserAccount().getPassword(), null);

				auditor.getUserAccount().setPassword(hashedPassword);

				this.auditorService.save(auditor);
				result = new ModelAndView("redirect:/");

			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndView(auditor, "actor.username.duplicated");
			} catch (final Throwable oops) {
				auditor.getUserAccount().setPassword(oldPassword);
				result = this.createEditModelAndView(auditor, "actor.commit.error");
			}
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Auditor auditor) {
		ModelAndView result;
		result = this.createEditModelAndView(auditor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Auditor auditor, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("sponsor/edit");
		result.addObject("actorClassName", "auditor");
		result.addObject("auditor", auditor);
		result.addObject("requestURI", "auditor/admin/create.do");
		result.addObject("actionURI", "auditor/admin/edit.do");

		result.addObject("messageCode", messageCode);

		return result;
	}

}
