
package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FolderService;
import domain.Folder;

@Controller
@RequestMapping("/folder/manager")
public class FolderManagerController {

	/* Services */
	@Autowired
	private FolderService	folderService;


	public FolderManagerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Folder> folders;

		folders = this.folderService.findAllByPrincipal();

		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);
		result.addObject("requestURI", "folder/manager/list.do");

		return result;

	}
}
