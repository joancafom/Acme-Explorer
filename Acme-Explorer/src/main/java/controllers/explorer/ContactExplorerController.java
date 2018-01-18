
package controllers.explorer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ExplorerService;
import controllers.AbstractController;
import domain.Contact;
import domain.Explorer;

@Controller
@RequestMapping("/contact/explorer")
public class ContactExplorerController extends AbstractController {

	/* Services */
	@Autowired
	private ExplorerService	explorerService;


	public ContactExplorerController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Contact contact = new Contact();
		result = this.createEditModelAndView(contact);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final String name, @RequestParam(required = false) final String email, @RequestParam(required = false) final String phoneNumber) {
		final ModelAndView result;
		Contact contact = new Contact();
		final Explorer explorer = this.explorerService.findByUserAccount(LoginService.getPrincipal());
		for (final Contact c : explorer.getEmergencyContacts())
			if (c.getEmail().equals(email) && c.getName().equals(name) && c.getPhoneNumber().equals(phoneNumber))
				contact = c;

		result = this.createEditModelAndView(contact);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Contact contact, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(contact);
		else
			try {
				final Explorer explorer = this.explorerService.findByUserAccount(LoginService.getPrincipal());
				explorer.getEmergencyContacts().add(contact);
				this.explorerService.save(explorer);
				result = new ModelAndView("redirect:/actor/explorer/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(contact, "message.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Contact contact, final BindingResult binding) {
		ModelAndView result;

		try {
			final Explorer explorer = this.explorerService.findByUserAccount(LoginService.getPrincipal());
			explorer.getEmergencyContacts().remove(contact);
			this.explorerService.save(explorer);
			result = new ModelAndView("redirect:/actor/explorer/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(contact, "message.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Contact contact) {
		ModelAndView result;
		result = this.createEditModelAndView(contact, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Contact contact, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("contact/edit");
		result.addObject("contact", contact);
		result.addObject("create", true);
		return result;
	}

}
