
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FolderService;
import domain.Folder;

@Controller
@RequestMapping("/folder")
public class FolderController extends AbstractController {

	/* Services */
	@Autowired
	private FolderService	folderService;


	public FolderController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Folder> folders;

		folders = this.folderService.findAllByPrincipal();

		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);
		result.addObject("requestURI", "folder/list.do");

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Folder folder;

		folder = this.folderService.create();
		result = this.createEditModelAndView(folder);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(folder);
		else
			try {
				this.folderService.save(folder);
				result = new ModelAndView("redirect:/folder/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folder, "message.commit.error");
			}

		return result;

	}

	private ModelAndView createEditModelAndView(final Folder folder) {
		ModelAndView result;
		result = this.createEditModelAndView(folder, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Folder folder, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("folder/edit");
		result.addObject("folder", folder);
		return result;
	}
}
