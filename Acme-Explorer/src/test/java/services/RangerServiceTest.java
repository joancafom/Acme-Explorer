
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RangerServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private RangerService	rangerService;


	// Supporting repositories -------------

	// Supporting services -----------------

	// Tests -------------------------------

	@Test
	public void testCreate() {
		final Ranger ranger;

		this.authenticate("admin1");

		final UserAccount userAccount = new UserAccount();

		ranger = this.rangerService.create(userAccount);

		Assert.isNull(ranger.getCurriculum());
		Assert.notNull(ranger.getTrips());
		Assert.isTrue(ranger.getTrips().isEmpty());

		this.unauthenticate();
	}

	@Test
	public void testFindAll() {
		// REVISAR !!!
		// Cómo se comprueba que el findAll() funciona correctamente?

		final Integer currentNumberOfRangersInTheXML = 4;

		this.authenticate("admin1");

		final Collection<Ranger> rangers = this.rangerService.findAll();

		Assert.notNull(rangers);
		Assert.isTrue(rangers.size() == currentNumberOfRangersInTheXML);

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {
		Ranger ranger1 = null;
		Ranger ranger2 = null;

		this.authenticate("admin1");

		final Collection<Ranger> rangers = this.rangerService.findAll();

		for (final Ranger r : rangers)
			if (r != null) {
				ranger1 = r;
				break;
			}

		ranger2 = this.rangerService.findOne(ranger1.getId());

		Assert.isTrue(ranger1.equals(ranger2));

		this.unauthenticate();
	}

	//	@Test
	//	public void testSave() {
	//		Ranger ranger1 = null;
	//		Collection<Ranger> rangers;
	//
	//		this.authenticate("admin1");
	//
	//		rangers = this.rangerRespository.findAll();
	//
	//		for (final Ranger r : rangers) {
	//			ranger1 = r;
	//			break;
	//		}
	//
	//		final String name = "Name";
	//		final String surname = "Surname";
	//		final String email = "email@gmail.com";
	//		final String phoneNumber = "954674359";
	//		final String address = "C/ Address Nº1 1ºA";
	//		final boolean isSuspicious = true;
	//
	//		ranger1.setAddress(address);
	//		ranger1.setEmail(email);
	//		ranger1.setIsSuspicious(isSuspicious);
	//		ranger1.setName(name);
	//		ranger1.setPhoneNumber(phoneNumber);
	//		ranger1.setSurname(surname);
	//
	//		final Ranger ranger2 = this.rangerService.save(ranger1);
	//
	//		Assert.isTrue(ranger2.getAddress().equals(address));
	//		Assert.isTrue(ranger2.getEmail().equals(email));
	//		Assert.isTrue(ranger2.getIsSuspicious());
	//		Assert.isTrue(ranger2.getName().equals(name));
	//		Assert.isTrue(ranger2.getPhoneNumber().equals(phoneNumber));
	//		Assert.isTrue(ranger2.getSurname().equals(surname));
	//
	//		this.unauthenticate();
	//	}

	@Test
	public void testFindByUserAccount() {
		Ranger ranger1 = null;
		Ranger ranger2 = null;

		this.authenticate("admin1");

		final Collection<Ranger> rangers = this.rangerService.findAll();

		for (final Ranger r : rangers)
			if (r != null) {
				ranger1 = r;
				break;
			}

		ranger2 = this.rangerService.findByUserAccount(ranger1.getUserAccount());

		Assert.isTrue(ranger1.equals(ranger2));

		this.unauthenticate();
	}

	//	@Test
	//	public void testFindAllSuspicious() {
	//		final Collection<Ranger> rangers;
	//		final Collection<Ranger> rangers2 = new ArrayList<Ranger>();
	//
	//		this.authenticate("admin1");
	//
	//		rangers = this.rangerService.findAllSuspicious();
	//
	//		for (final Ranger r : this.rangerRespository.findAll())
	//			if (r.getIsSuspicious())
	//				rangers2.add(r);
	//
	//		Assert.isTrue(rangers2.containsAll(rangers));
	//
	//		this.unauthenticate();
	//	}
}
