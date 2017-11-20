
package services;

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
import domain.Explorer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ExplorerServiceTest extends AbstractTest {

	@Autowired
	private ExplorerService	explorerService;


	@Test
	public void testCreate() {
		super.authenticate("admin1");

		final Explorer explorer = this.explorerService.create();
		Assert.notNull(explorer.getSentMessages());
		Assert.notNull(explorer.getReceivedMessages());
		Assert.notNull(explorer.getEmergencyContacts());
		Assert.notNull(explorer.getStories());
		Assert.notNull(explorer.getSurvivalClasses());
		Assert.notNull(explorer.getTripApplications());

		this.unauthenticate();

	}

	@Test
	public void testFindByUserAccount() {
		super.authenticate("explorer1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer e = this.explorerService.findByUserAccount(userAccount);
		Assert.isTrue(e.getUserAccount().getUsername().equals("explorer1"));
		this.unauthenticate();

	}

}
