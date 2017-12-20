
package controllers.ranger;

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

import services.ActorService;
import services.FolderService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Folder;
import domain.Message;
import domain.PriorityLevel;

@Controller
@RequestMapping("/message/ranger")
public class MessageRangerController extends AbstractController {

	/* Services */
	@Autowired
	private MessageService	messageService;

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;


	public MessageRangerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int folderId) {
		final ModelAndView result;
		final Collection<Message> messages;
		final Folder folder = this.folderService.findOne(folderId);

		messages = this.messageService.findByFolder(folder);

		result = new ModelAndView();
		result.addObject("messages", messages);
		result.addObject("folderId", folderId);

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

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Message message;

		message = this.messageService.create();
		result = this.createEditModelAndView(message);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int messageId) {
		ModelAndView result;
		Message message;

		message = this.messageService.findOne(messageId);
		Assert.notNull(message);
		result = this.createEditModelAndView(message);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Message message, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {
				if (message.getId() == 0)
					this.messageService.send(message.getRecipient(), message);

				this.messageService.save(message);
				result = new ModelAndView("redirect:/folder/ranger/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "message.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Message message, final BindingResult binding) {
		ModelAndView result;

		try {
			this.messageService.delete(message);
			result = new ModelAndView("redirect:/folder/ranger/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(message, "message.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message) {
		ModelAndView result;
		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message, final String messageCode) {
		final ModelAndView result;

		final Collection<Actor> actors;
		final PriorityLevel[] priorities;
		final Collection<Folder> folders;

		actors = this.actorService.findAll();
		priorities = PriorityLevel.values();
		folders = this.folderService.findAllByPrincipal();

		result = new ModelAndView("message/edit");
		result.addObject("message", message);
		result.addObject("actors", actors);
		result.addObject("priorities", priorities);
		result.addObject("folders", folders);
		result.addObject("messageCode", messageCode);

		return result;

	}

}
