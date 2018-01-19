
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
import services.SponsorService;
import services.UserAccountService;
import domain.Sponsor;

@Controller
@RequestMapping("/sponsor/admin")
public class SponsorAdminController {

	/* Services */
	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private UserAccountService	userAccountService;


	public SponsorAdminController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		UserAccount userAccount;
		Sponsor sponsor;

		userAccount = this.userAccountService.create();
		sponsor = this.sponsorService.create(userAccount);

		result = this.createEditModelAndView(sponsor);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsor sponsor, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(sponsor);
		else {

			final String hashedPassword;
			final String oldPassword = sponsor.getUserAccount().getPassword();

			try {

				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				hashedPassword = encoder.encodePassword(sponsor.getUserAccount().getPassword(), null);

				sponsor.getUserAccount().setPassword(hashedPassword);

				this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:/");

			} catch (final DataIntegrityViolationException oops) {
				result = this.createEditModelAndView(sponsor, "actor.username.duplicated");
			} catch (final Throwable oops) {
				sponsor.getUserAccount().setPassword(oldPassword);
				result = this.createEditModelAndView(sponsor, "actor.commit.error");
			}
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsor sponsor) {
		ModelAndView result;
		result = this.createEditModelAndView(sponsor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsor sponsor, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("sponsor/edit");
		result.addObject("actorClassName", "sponsor");
		result.addObject("sponsor", sponsor);
		result.addObject("requestURI", "sponsor/admin/create.do");
		result.addObject("actionURI", "sponsor/admin/edit.do");

		result.addObject("messageCode", messageCode);

		return result;
	}

}
