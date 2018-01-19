
package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SystemConfigurationService;
import controllers.AbstractController;
import domain.SystemConfiguration;

@Controller
@RequestMapping("/systemConfiguration/admin")
public class SystemConfigurationAdminController extends AbstractController {

	/* Services */
	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		final SystemConfiguration sysConfig = this.systemConfigurationService.getCurrentSystemConfiguration();

		Assert.notNull(sysConfig);

		result = new ModelAndView("systemConfiguration/display");
		result.addObject("systemConfiguration", sysConfig);

		return result;
	}

	// Edition --------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView res;
		final SystemConfiguration sysConfig = this.systemConfigurationService.getCurrentSystemConfiguration();

		Assert.notNull(sysConfig);

		res = this.createEditModelAndView(sysConfig);

		return res;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SystemConfiguration sysConfig, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(sysConfig);
		else
			try {
				this.systemConfigurationService.save(sysConfig);
				res = new ModelAndView("redirect:/systemConfiguration/admin/display.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(sysConfig, "sysConfig.commit.error");
			}

		return res;
	}

	// Ancillary methods ----------------------

	protected ModelAndView createEditModelAndView(final SystemConfiguration sysConfig) {
		ModelAndView res;

		res = this.createEditModelAndView(sysConfig, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final SystemConfiguration sysConfig, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("systemConfiguration/edit");
		res.addObject("systemConfiguration", sysConfig);
		res.addObject("messageCode", messageCode);

		return res;
	}

}
