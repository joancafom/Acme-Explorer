
package controllers.explorer;

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
import services.ExplorerService;
import services.FinderService;
import controllers.AbstractController;
import domain.Explorer;
import domain.Finder;

@Controller
@RequestMapping("/finder/explorer")
public class FinderExplorerController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private FinderService	finderService;

	@Autowired
	private ExplorerService	explorerService;


	// Constructors ---------------------------

	public FinderExplorerController() {
		super();
	}

	// Display --------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) Integer finderId) {
		final ModelAndView res;
		final Finder finder;
		final Explorer explorer;

		final UserAccount userAccount = LoginService.getPrincipal();
		explorer = this.explorerService.findByUserAccount(userAccount);

		if (finderId == null)
			finderId = explorer.getFinder().getId();

		finder = this.finderService.findOne(finderId);

		Assert.isTrue(explorer.getFinder().getId() == finderId);

		res = new ModelAndView("finder/display");
		res.addObject("finder", finder);
		res.addObject("RequestURI", "finder/explorer/display.do?finderId=" + finder.getId());

		return res;
	}
	// Creation -------------------------------

	// Edition --------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int finderId) {
		ModelAndView res;
		final Finder finder;
		Explorer explorer;

		final UserAccount userAccount = LoginService.getPrincipal();
		explorer = this.explorerService.findByUserAccount(userAccount);

		finder = this.finderService.findOne(finderId);
		Assert.notNull(finder);

		Assert.isTrue(explorer.getFinder().getId() == finderId);

		res = this.createEditModelAndView(finder);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(finder);
		else
			try {
				this.finderService.save(finder);
				res = new ModelAndView("redirect:/finder/explorer/display.do?finderId=" + finder.getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(finder, "finder.commit.error");
			}

		return res;
	}

	// Ancillary methods ----------------------

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView res;

		res = this.createEditModelAndView(finder, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("finder/edit");
		res.addObject("finder", finder);
		res.addObject("messageCode", messageCode);

		return res;
	}

}
