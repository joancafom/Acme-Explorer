
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Actor;
import domain.Explorer;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService	messageService;
	@Autowired
	private ExplorerService	explorerService;
	@Autowired
	private ActorService	actorService;


	@Test
	public void testCreate() {
		super.authenticate("explorer1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);
		final Message message = this.messageService.create();
		Assert.notNull(message);
		Assert.isTrue(message.getSender().equals(explorer));
	}

	@Test
	public void testSave() {
		super.authenticate("explorer1");
		final Message message = this.messageService.create();
		message.setBody("Melodrama");
		this.messageService.save(message);
		Assert.isTrue(message.getBody().equals("Melodrama"));

	}

	@Test
	public void testDelete() {
		super.authenticate("explorer1");
		final Message message = this.messageService.create();
		this.messageService.delete(message);
	}

	@Test
	public void testSend() {
		super.authenticate("explorer1");
		final UserAccount userAccount1 = LoginService.getPrincipal();
		final Explorer explorer1 = this.explorerService.findByUserAccount(userAccount1);
		super.authenticate("explorer2");
		final UserAccount userAccount2 = LoginService.getPrincipal();
		final Explorer explorer2 = this.explorerService.findByUserAccount(userAccount2);
		super.authenticate("explorer1");

		final Message message = this.messageService.create();
		message.setSubject("Hey hey heyyyyyy!!");
		message.setBody("Por fin funciona el test oleeeeee");
		this.messageService.send(explorer2, message);

		Assert.isTrue(explorer1.getSentMessages().contains(message));
		Assert.isTrue(explorer2.getReceivedMessages().contains(message));

	}

	@Test
	public void testSendNotification() {
		super.authenticate("admin1");
		final Message message = this.messageService.create();
		final List<Actor> actors = new ArrayList<Actor>(this.actorService.findAll());
		final Actor actor = actors.get(0);
		this.messageService.sendNotification(actor, message);

	}

}
