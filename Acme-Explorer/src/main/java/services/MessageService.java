
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

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
	private MessageRepository			messageRepository;

	//External Services
	@Autowired
	private ActorService				actorService;
	@Autowired
	private FolderService				folderService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	/* Crud */

	public Message create() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Actor actorSender = this.actorService.findByUserAccount(userAccount);

		final Message message = new Message();
		message.setSender(actorSender);
		//El mensaje se setea luego al hacer send()
		message.setSentMoment(new Date());
		final Folder outBox = this.folderService.findByActorAndName(actorSender, "Out Box");
		message.setFolder(outBox);

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

		if (message.getFolder().getName().equals("Trash Box"))
			this.messageRepository.delete(message);

		else {
			final Folder trashBox = this.folderService.findByActorAndName(actor, "Trash Box");
			message.setFolder(trashBox);
			this.save(message);
		}
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
		final Message messageReceiver = this.create();
		messageReceiver.setBody(messageSender.getBody());
		messageReceiver.setPriority(messageSender.getPriority());
		messageReceiver.setRecipient(messageSender.getRecipient());
		messageReceiver.setSender(messageSender.getSender());
		messageReceiver.setSubject(messageSender.getSubject());

		/* 5. We set the sent moment */
		messageSender.setSentMoment(new Date());
		messageReceiver.setSentMoment(new Date());

		/* 6. We check if the message contains spam words */
		Boolean spam = false;
		for (final String spamWord : this.systemConfigurationService.getCurrentSystemConfiguration().getSpamWords())
			if (messageSender.getSubject().toLowerCase().contains(spamWord)) {
				spam = true;
				break;
			} else if (messageSender.getBody().toLowerCase().contains(spamWord)) {
				spam = true;
				break;
			}

		/* 6. We add the messages to their folders and collections */
		sender.getSentMessages().add(messageSender);
		final Folder folderSenderOutbox = this.folderService.findByActorAndName(sender, "Out Box");
		messageSender.setFolder(folderSenderOutbox);

		receiver.getReceivedMessages().add(messageReceiver);
		final Folder folderReceiverInbox;
		if (spam == true)
			folderReceiverInbox = this.folderService.findByActorAndName(receiver, "Spam Box");
		else
			folderReceiverInbox = this.folderService.findByActorAndName(receiver, "In Box");
		messageReceiver.setFolder(folderReceiverInbox);

		this.save(messageReceiver);
	}

	public void sendNotification(final Message messageSender) {

		/* 1. We check that the receiver and the message to send are not null */
		Assert.notNull(messageSender);
		Assert.notNull(messageSender.getRecipient());

		/* 2. We check that there's a user logged */
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		/* 3. We get the actor who's logged */
		final Actor sender = this.actorService.findByUserAccount(userAccount);

		/* 4. We duplicate the message for the receiver */
		final Message messageReceiver = this.create();
		messageReceiver.setBody(messageSender.getBody());
		messageReceiver.setPriority(messageSender.getPriority());
		messageReceiver.setRecipient(messageSender.getRecipient());
		messageReceiver.setSender(messageSender.getSender());
		messageReceiver.setSubject(messageSender.getSubject());

		/* 5. We set the sent moment */
		messageSender.setSentMoment(new Date());
		messageReceiver.setSentMoment(new Date());

		/* 6. We add the messages to their folders and collections */
		sender.getSentMessages().add(messageSender);
		final Folder folderSenderOutBox = this.folderService.findByActorAndName(sender, "Out Box");
		messageSender.setFolder(folderSenderOutBox);

		messageSender.getRecipient().getReceivedMessages().add(messageReceiver);
		final Folder folderReceiverNotificationBox = this.folderService.findByActorAndName(messageSender.getRecipient(), "Notification Box");
		messageReceiver.setFolder(folderReceiverNotificationBox);

		this.save(messageSender);
		this.save(messageReceiver);

	}

	public Collection<Message> findByFolder(final Folder folder) {
		Assert.notNull(folder);
		Collection<Message> messages = new HashSet<Message>();
		messages = this.messageRepository.findByFolder(folder.getId());

		return messages;
	}

}
