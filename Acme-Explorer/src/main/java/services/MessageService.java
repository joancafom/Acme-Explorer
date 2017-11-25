
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//Managed Repository
	@Autowired
	private MessageRepository	messageRepository;

	//External Services
	@Autowired
	private ActorService		actorService;
	@Autowired
	private FolderService		folderService;


	//Crud
	public Message create() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Actor actorSender = this.actorService.findByUserAccount(userAccount);

		final Message message = new Message();
		message.setSender(actorSender);

		return message;

	}

	public Message save(final Message message) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		message.setSentMoment(new Date());

		return this.messageRepository.save(message);
	}

	public void delete(final Message message) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Actor actor = this.actorService.findByUserAccount(userAccount);
		Assert.isTrue(message.getSender().equals(actor));

		this.messageRepository.delete(message);
	}

	//Other business methods

	public void send(final Actor receiver, final Message messageSender) {
		Assert.notNull(receiver);
		Assert.notNull(messageSender);

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Actor sender = this.actorService.findByUserAccount(userAccount);

		final Message messageReceiver = messageSender;
		messageSender.setSentMoment(new Date());
		messageReceiver.setSentMoment(new Date());

		sender.getSentMessages().add(messageSender);
		final Folder folderSenderInbox = this.folderService.findByActorAndName(sender, "Out Box");
		messageSender.setFolder(folderSenderInbox);

		receiver.getReceivedMessages().add(messageReceiver);
		final Folder folderReceiverOutbox = this.folderService.findByActorAndName(receiver, "In Box");
		messageReceiver.setFolder(folderReceiverOutbox);

	}

	public void sendNotification(final Actor receiver, final Message messageSender) {
		final UserAccount userAccount = LoginService.getPrincipal();

		final Actor sender = this.actorService.findByUserAccount(userAccount);

		final Message messageReceiver = this.create();
		messageReceiver.setBody(messageSender.getBody());
		messageReceiver.setPriority(messageSender.getPriority());
		messageReceiver.setRecipient(messageSender.getRecipient());
		messageReceiver.setSender(messageSender.getSender());
		messageReceiver.setSubject(messageSender.getSubject());

		messageSender.setSentMoment(new Date());
		messageReceiver.setSentMoment(new Date());

		final Folder folderSenderInbox = this.folderService.findByActorAndName(sender, "In Box");
		messageSender.setFolder(folderSenderInbox);
		sender.getSentMessages().add(messageSender);

		final Folder folderReceiverOutbox = this.folderService.findByActorAndName(receiver, "Notification Box");
		messageReceiver.setFolder(folderReceiverOutbox);
		receiver.getReceivedMessages().add(messageReceiver);

	}

	public void changeMessageFolder(final Message message, final Folder folder) {
		Assert.notNull(message);
		Assert.notNull(folder);

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Actor actor = this.actorService.findByUserAccount(userAccount);

		Assert.isTrue(actor.equals(message.getSender()));
		Assert.isTrue(actor.getSentMessages().contains(message));
		Assert.isTrue(actor.getFolders().contains(folder));

		message.setFolder(folder);

	}

}
