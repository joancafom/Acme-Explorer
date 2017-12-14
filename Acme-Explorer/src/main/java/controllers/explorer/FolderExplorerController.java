
package controllers.explorer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.FolderService;
import domain.Folder;

@Controller
@RequestMapping("/folder/explorer")
public class FolderExplorerController extends AbstractController {

	/* Services */
	@Autowired
	private FolderService	folderService;


	public FolderExplorerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Folder> folders;

		folders = this.folderService.findAllByPrincipal();

		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);
		result.addObject("requestURI", "folder/explorer/list.do");

		return result;

	}
}
