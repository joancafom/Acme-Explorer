
package controllers;

import java.util.Collection;

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
import services.ActorService;
import services.FolderService;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Controller
@RequestMapping("/folder")
public class FolderController extends AbstractController {

	/* Services */
	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;


	public FolderController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer folderId) {
		final ModelAndView result;
		Collection<Folder> folders;
		Collection<Message> messages;

		if (folderId == null) {
			folders = this.folderService.findAllParentFoldersByPrincipal();
			result = new ModelAndView("folder/list");

		} else {

			final Folder parentFolder = this.folderService.findOne(folderId);
			messages = parentFolder.getMessages();
			folders = parentFolder.getChildFolders();

			result = new ModelAndView("folder/list");
			result.addObject("messages", messages);
		}
		result.addObject("folderId", folderId);
		result.addObject("folders", folders);

		return result;

	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false) final Integer folderId) {
		final ModelAndView result;
		Folder folder;
		final Folder folderParent;

		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor actor = this.actorService.findByUserAccount(userAccount);

		if (folderId != null) {
			folderParent = this.folderService.findOne(folderId);
			folder = this.folderService.create(actor, folderParent);
		} else
			folder = this.folderService.create(actor, null);

		result = this.createEditModelAndView(folder);

		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int folderId) {
		ModelAndView result;
		Folder folder;

		folder = this.folderService.findOne(folderId);
		Assert.notNull(folder);
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

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Folder folder, final BindingResult binding) {
		ModelAndView result;

		try {
			this.folderService.deleteByPrincipal(folder);
			result = new ModelAndView("redirect:/folder/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(folder, "message.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder) {
		ModelAndView result;
		result = this.createEditModelAndView(folder, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("folder/edit");
		result.addObject("folder", folder);
		result.addObject("messageCode", messageCode);
		return result;
	}
}
