
package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FolderService;
import services.MessageService;
import domain.Folder;
import domain.Message;

@Controller
@RequestMapping("/message/manager")
public class MessageManagerController {

	/* Services */
	@Autowired
	private MessageService	messageService;

	@Autowired
	private FolderService	folderService;


	public MessageManagerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int folderId) {
		final ModelAndView result;
		final Collection<Message> messages;
		final Folder folder = this.folderService.findOne(folderId);

		messages = this.messageService.findByFolder(folder);

		result = new ModelAndView("message/list");
		result.addObject("messages", messages);
		result.addObject("folderId", folderId);
		result.addObject("requestURI", "message/manager/list.do?folderId=" + folderId);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int messageId) {
		final ModelAndView result;
		final Message message;

		message = this.messageService.findOne(messageId);

		result = new ModelAndView("message/display");
		result.addObject("messageDisplay", message);

		return result;
	}
}
