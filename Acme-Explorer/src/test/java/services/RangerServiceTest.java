
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.RangerRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import utilities.AbstractTest;
import domain.Folder;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RangerServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private RangerService			rangerService;

	// Supporting repositories -------------

	@Autowired
	private UserAccountRepository	userAccountRepository;

	@Autowired
	private RangerRepository		rangerRespository;


	// Supporting services -----------------

	// Tests -------------------------------

	@Test
	public void testCreate() {
		Ranger ranger;
		final List<String> systemFolderNames = Arrays.asList("In Box", "Out Box", "Notification Box", "Trash Box", "Spam Box");
		final List<String> folderNames = new ArrayList<String>();

		this.authenticate("admin1");

		ranger = this.rangerService.create();
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
	public void testSave() {
		Ranger ranger = null;
		Collection<Ranger> rangers;

		this.authenticate("admin1");

		rangers = this.rangerRespository.findAll();

		for (final Ranger r : rangers) {
			ranger = r;
			break;
		}

		final String name = "Name";
		final String surname = "Surname";
		final String email = "email@gmail.com";
		final String phoneNumber = "954674359";
		final String address = "C/ Address Nº1 1ºA";
		final boolean isSuspicious = true;

		ranger.setAddress(address);
		ranger.setEmail(email);
		ranger.setIsSuspicious(isSuspicious);
		ranger.setName(name);
		ranger.setPhoneNumber(phoneNumber);
		ranger.setSurname(surname);

		final Ranger rangerS = this.rangerService.save(ranger);

		Assert.isTrue(rangerS.getAddress().equals(address));
		Assert.isTrue(rangerS.getEmail().equals(email));
		Assert.isTrue(rangerS.getIsSuspicious());
		Assert.isTrue(rangerS.getName().equals(name));
		Assert.isTrue(rangerS.getPhoneNumber().equals(phoneNumber));
		Assert.isTrue(rangerS.getSurname().equals(surname));

		this.unauthenticate();
	}

	@Test
	public void testFindByUserAccount() {
		this.authenticate("ranger1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(this.userAccountRepository.findByUsername("ranger1"));

		Assert.notNull(ranger);
		Assert.isTrue(userAccount.equals(ranger.getUserAccount()));

		this.unauthenticate();
	}

	@Test
	public void testFindAllSuspicious() {
		final Collection<Ranger> rangers;
		final Collection<Ranger> rangers2 = new ArrayList<Ranger>();

		this.authenticate("admin1");

		rangers = this.rangerService.findAllSuspicious();

		for (final Ranger r : this.rangerRespository.findAll())
			if (r.getIsSuspicious())
				rangers2.add(r);

		Assert.isTrue(rangers2.containsAll(rangers));

		this.unauthenticate();
	}
}
