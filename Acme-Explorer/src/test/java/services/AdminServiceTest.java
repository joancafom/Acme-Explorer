
package services;

import java.util.Collection;

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
import domain.Admin;
import domain.Message;
import domain.PriorityLevel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdminServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private AdminService	adminService;

	// Supporting Services -------------
	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	// Tests -------------------------------

	@Test
	public void testFindByUserAccount() {
		this.authenticate("admin1");

		final UserAccount userAccount = LoginService.getPrincipal();

		final Admin foundMe = this.adminService.findByUserAccount(userAccount);

		Assert.notNull(foundMe);
		Assert.isTrue(userAccount.equals(foundMe.getUserAccount()));

		this.unauthenticate();
	}

	@Test
	public void testBroadcastNotification() {

		this.authenticate("admin1");

		final UserAccount userAccount = LoginService.getPrincipal();

		final Admin me = this.adminService.findByUserAccount(userAccount);

		final Message notifactionMessage = this.messageService.create();

		notifactionMessage.setSubject("Your US password will expire next year");
		notifactionMessage.setBody("According to the US privacy policy, you will need to change your password ");
		notifactionMessage.setPriority(PriorityLevel.HIGH);

		this.adminService.broadcastNotification(notifactionMessage);

		//We check that all the actors have the message

		final Collection<Actor> allActors = this.actorService.findAll();
		allActors.remove(me);

		for (final Actor a : allActors)
			Assert.isTrue(a.getReceivedMessages().contains(notifactionMessage));

		this.unauthenticate();

	}

	@Test
	public void testDashboardInformation() {

		this.authenticate("admin1");

		final Collection<Object> dashInfo = this.adminService.dashboardInformation();

		Assert.notNull(dashInfo);
		Assert.notNull(dashInfo.size() == 8);

		this.unauthenticate();

	}
}
