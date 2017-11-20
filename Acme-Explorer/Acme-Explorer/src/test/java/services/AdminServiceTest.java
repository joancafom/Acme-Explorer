
package services;

import java.util.ArrayList;
import java.util.Arrays;
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
import security.UserAccountRepository;
import utilities.AbstractTest;
import domain.Admin;
import domain.Folder;
import domain.Manager;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdminServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private AdminService			adminService;

	// Supporting repositories -------------

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Supporting services -----------------

	// Tests -------------------------------

	@Test
	public void testCreateManager() {
		Manager manager;
		final List<String> systemFolderNames = Arrays.asList("In Box", "Out Box", "Notification Box", "Trash Box", "Spam Box");
		final List<String> folderNames = new ArrayList<String>();

		this.authenticate("admin1");

		manager = this.adminService.createManager();
		Assert.isNull(manager.getName());
		Assert.isNull(manager.getSurname());
		Assert.isNull(manager.getEmail());
		Assert.isNull(manager.getPhoneNumber());
		Assert.isNull(manager.getAddress());
		Assert.isTrue(!manager.getIsSuspicious());
		Assert.isTrue(!manager.getIsBanned());

		Assert.notNull(manager.getSocialIDs());
		Assert.isTrue(manager.getSocialIDs().isEmpty());

		Assert.notNull(manager.getFolders());
		Assert.isTrue(!manager.getFolders().isEmpty());
		Assert.isTrue(manager.getFolders().size() == 5);

		for (final Folder f : manager.getFolders()) {
			folderNames.add(f.getName());

			Assert.isTrue(f.getIsSystem());

			Assert.notNull(f.getMessages());
			Assert.isTrue(f.getMessages().isEmpty());

			Assert.notNull(f.getChildFolders());
			Assert.isTrue(f.getChildFolders().isEmpty());
		}

		Assert.isTrue(systemFolderNames.equals(folderNames));

		Assert.notNull(manager.getSentMessages());
		Assert.isTrue(manager.getSentMessages().isEmpty());

		Assert.notNull(manager.getReceivedMessages());
		Assert.isTrue(manager.getReceivedMessages().isEmpty());

		Assert.notNull(manager.getUserAccount());
		Assert.isNull(manager.getUserAccount().getUsername());
		Assert.isNull(manager.getUserAccount().getPassword());
		Assert.notNull(manager.getUserAccount().getAuthorities());
		Assert.isTrue(manager.getUserAccount().getAuthorities().toString().contains("MANAGER"));

		Assert.notNull(manager.getSurvivalClasses());
		Assert.isTrue(manager.getSurvivalClasses().isEmpty());

		Assert.notNull(manager.getTrips());
		Assert.isTrue(manager.getTrips().isEmpty());

		this.unauthenticate();
	}

	@Test
	public void testCreateRanger() {
		Ranger ranger;
		final List<String> systemFolderNames = Arrays.asList("In Box", "Out Box", "Notification Box", "Trash Box", "Spam Box");
		final List<String> folderNames = new ArrayList<String>();

		this.authenticate("admin1");

		ranger = this.adminService.createRanger();
		Assert.isNull(ranger.getName());
		Assert.isNull(ranger.getSurname());
		Assert.isNull(ranger.getEmail());
		Assert.isNull(ranger.getPhoneNumber());
		Assert.isNull(ranger.getAddress());
		Assert.isTrue(!ranger.getIsSuspicious());
		Assert.isTrue(!ranger.getIsBanned());

		Assert.notNull(ranger.getSocialIDs());
		Assert.isTrue(ranger.getSocialIDs().isEmpty());

		Assert.notNull(ranger.getFolders());
		Assert.isTrue(!ranger.getFolders().isEmpty());
		Assert.isTrue(ranger.getFolders().size() == 5);

		for (final Folder f : ranger.getFolders()) {
			folderNames.add(f.getName());

			Assert.isTrue(f.getIsSystem());

			Assert.notNull(f.getMessages());
			Assert.isTrue(f.getMessages().isEmpty());

			Assert.notNull(f.getChildFolders());
			Assert.isTrue(f.getChildFolders().isEmpty());
		}

		Assert.isTrue(systemFolderNames.equals(folderNames));

		Assert.notNull(ranger.getSentMessages());
		Assert.isTrue(ranger.getSentMessages().isEmpty());

		Assert.notNull(ranger.getReceivedMessages());
		Assert.isTrue(ranger.getReceivedMessages().isEmpty());

		Assert.notNull(ranger.getUserAccount());
		Assert.isNull(ranger.getUserAccount().getUsername());
		Assert.isNull(ranger.getUserAccount().getPassword());
		Assert.notNull(ranger.getUserAccount().getAuthorities());
		Assert.isTrue(ranger.getUserAccount().getAuthorities().toString().contains("RANGER"));

		Assert.isNull(ranger.getCurriculum());

		Assert.notNull(ranger.getTrips());
		Assert.isTrue(ranger.getTrips().isEmpty());

		this.unauthenticate();
	}

	@Test
	public void testFindByUserAccount() {
		this.authenticate("admin1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Admin admin = this.adminService.findByUserAccount(this.userAccountRepository.findByUsername("admin1"));

		Assert.notNull(admin);
		Assert.isTrue(userAccount.equals(admin.getUserAccount()));

		this.unauthenticate();
	}
}
