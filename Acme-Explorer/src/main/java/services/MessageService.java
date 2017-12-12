
package services;

import java.util.Collection;
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


	/* Crud */

	public Message create() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Actor actorSender = this.actorService.findByUserAccount(userAccount);

		final Message message = new Message();
		message.setSender(actorSender);

		return message;

	}

	public Message findOne(final int messageId) {
		/* 1. We check that the user is logged */
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		/* 2. We get the actor who is logged */
		final Actor actor = this.actorService.findByUserAccount(userAccount);

		/* 3. We get the message with the repository */
		final Message message = this.messageRepository.findOne(messageId);

		/* 4. We check that the message is from the logged user */
		Assert.isTrue(actor.getSentMessages().contains(message) || actor.getReceivedMessages().contains(message));
		return message;
	}

	public Collection<Message> findAll() {
		/* 1. We check that the user is logged */
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		/* 2. We get the actor who is logged */
		final Actor actor = this.actorService.findByUserAccount(userAccount);

		/* 3. We get the messages with the repository */
		final Collection<Message> messages = this.messageRepository.findAllByActor(actor.getId());

		return messages;
	}

	public Message save(final Message message) {
		/* 1. We check that the user is logged */
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		/* 2. We check that the message given is not null */
		Assert.notNull(message);

		return this.messageRepository.save(message);
	}

	public void delete(final Message message) {
		/* 1. We check that the user is logged */
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		/* 2. We get the user that is logged */
		final Actor actor = this.actorService.findByUserAccount(userAccount);

		/*
		 * 3. We check that the sender of the message is the actor logged, and that
		 * the message given is not null
		 */
		Assert.notNull(message);
		Assert.isTrue(message.getSender().equals(actor));

		this.messageRepository.delete(message);
	}

	/* Other business methods */

	public void send(final Actor receiver, final Message messageSender) {
		/* 1. We check that the receiver and the message to send are not null */
		Assert.notNull(receiver);
		Assert.notNull(messageSender);

		/* 2. We check that there's a user logged */
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		/* 3. We get the actor who's logged */
		final Actor sender = this.actorService.findByUserAccount(userAccount);

		/* 4. We duplicate the message for the receiver */
		final Message messageReceiver = messageSender;

		/* 5. We set the sent moment */
		messageSender.setSentMoment(new Date());
		messageReceiver.setSentMoment(new Date());

		/* 6. We add the messages to their folders and collections */
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
		Assert.isTrue(actor.getFolders().contains(folder));

		message.setFolder(folder);

	}

}
