
package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.ActorService;
import services.ManagerService;
import services.UserAccountService;
import controllers.AbstractController;
import domain.Manager;

@Controller
@RequestMapping("/manager/admin")
public class ManagerAdminController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private ActorService		actorService;


	// Constructors ---------------------------

	public ManagerAdminController() {
		super();
	}

	// Display --------------------------------

	// Creation -------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		final ModelAndView res;
		final Manager manager;
		final UserAccount userAccount;

		userAccount = this.userAccountService.create();
		manager = this.managerService.create(userAccount);

		res = this.createEditModelAndView(manager);

		return res;

	}

	// Edition --------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int managerId) {
		ModelAndView res;
		final Manager manager;

		manager = this.managerService.findOne(managerId);
		Assert.notNull(manager);

		res = this.createEditModelAndView(manager);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Manager manager, final BindingResult bindingResult) {

		ModelAndView res;

		if (bindingResult.hasErrors())
			res = this.createEditModelAndView(manager);
		else {

			final String hashedPassword;
			final String oldPassword = manager.getUserAccount().getPassword();

			try {

				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				hashedPassword = encoder.encodePassword(manager.getUserAccount().getPassword(), null);

				manager.getUserAccount().setPassword(hashedPassword);

				this.managerService.save(manager);
				res = new ModelAndView("redirect:/");

			} catch (final DataIntegrityViolationException oops) {
				manager.getUserAccount().setPassword(oldPassword);
				res = this.createEditModelAndView(manager, "actor.username.duplicated");
			} catch (final Throwable oops) {
				manager.getUserAccount().setPassword(oldPassword);
				res = this.createEditModelAndView(manager, "actor.commit.error");
			}
		}

		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "ban")
	public ModelAndView ban(@Valid final Manager manager, final BindingResult bindingResult) {

		ModelAndView res;

		if (bindingResult.hasErrors())
			res = this.createEditModelAndView(manager);
		else
			try {

				this.actorService.ban(manager);
				res = new ModelAndView("redirect:/actor/admin/listSuspicious.do");

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(manager, "actor.commit.error");
			}

		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "unban")
	public ModelAndView unban(@Valid final Manager manager, final BindingResult bindingResult) {

		ModelAndView res;

		if (bindingResult.hasErrors())
			res = this.createEditModelAndView(manager);
		else
			try {

				this.actorService.unban(manager);
				res = new ModelAndView("redirect:/actor/admin/listSuspicious.do");

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(manager, "actor.commit.error");
			}

		return res;

	}

	// Ancillary methods ----------------------

	protected ModelAndView createEditModelAndView(final Manager manager) {
		return this.createEditModelAndView(manager, null);
	}

	protected ModelAndView createEditModelAndView(final Manager manager, final String messageCode) {
		final ModelAndView res;

		res = new ModelAndView("manager/edit");
		res.addObject("actorClassName", "manager");
		res.addObject("manager", manager);
		res.addObject("requestURI", "manager/admin/create.do");
		res.addObject("actionURI", "manager/admin/edit.do");

		res.addObject("messageCode", messageCode);

		return res;
	}
}
