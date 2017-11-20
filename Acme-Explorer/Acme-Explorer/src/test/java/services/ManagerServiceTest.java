
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ManagerServiceTest extends AbstractTest {

	//Service under test
	@Autowired
	private ManagerService	managerService;


	//Other required Services

	@Test
	public void testCreate() {
		this.authenticate("admin1");

		final Manager testManager = this.managerService.create();

		Assert.notNull(testManager);
		Assert.isNull(testManager.getName());
		Assert.isNull(testManager.getSurname());
		Assert.isNull(testManager.getEmail());
		Assert.isNull(testManager.getPhoneNumber());
		Assert.isNull(testManager.getAddress());
		Assert.notNull(testManager.getFolders());
		Assert.isTrue(!testManager.getFolders().isEmpty());
		Assert.notNull(testManager.getReceivedMessages());
		Assert.isTrue(testManager.getReceivedMessages().isEmpty());
		Assert.notNull(testManager.getSentMessages());
		Assert.isTrue(testManager.getSentMessages().isEmpty());
		Assert.notNull(testManager.getSocialIDs());
		Assert.isTrue(testManager.getSocialIDs().isEmpty());
		Assert.notNull(testManager.getTrips());
		Assert.isTrue(testManager.getTrips().isEmpty());
		Assert.notNull(testManager.getSurvivalClasses());
		Assert.isTrue(testManager.getSurvivalClasses().isEmpty());

		this.unauthenticate();
	}
}
