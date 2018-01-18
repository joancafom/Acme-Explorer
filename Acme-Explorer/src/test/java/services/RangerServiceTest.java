
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
import domain.Curriculum;
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

	@Test
	public void testSave() {
		// REVISAR !!!
		// Qué se debe comprobar en el save?
		Ranger ranger1 = null;
		Ranger ranger2 = null;

		this.authenticate("ranger1");

		final Collection<Ranger> rangers = this.rangerService.findAll();

		for (final Ranger r : rangers)
			if (r != null) {
				ranger1 = r;
				break;
			}

		ranger1.setName("Name");
		ranger1.setSurname("Surname");
		ranger1.setEmail("email@gmail.com");
		ranger1.setPhoneNumber("954673648");
		ranger1.setIsSuspicious(true);
		// REVISAR !!!
		// Se puede cambiar el currículum de un ranger a otro?
		ranger1.setCurriculum(new Curriculum());
		ranger1.getTrips().remove(ranger1.getTrips().toArray()[0]);

		ranger2 = this.rangerService.save(ranger1);

		Assert.notNull(ranger2);
		Assert.isTrue(ranger1.getName().equals(ranger2.getName()));
		Assert.isTrue(ranger1.getSurname().equals(ranger2.getSurname()));
		Assert.isTrue(ranger1.getEmail().equals(ranger2.getEmail()));
		Assert.isTrue(ranger1.getPhoneNumber().equals(ranger2.getPhoneNumber()));
		Assert.isTrue(ranger1.getIsSuspicious() == ranger2.getIsSuspicious());
		Assert.isTrue(ranger1.getCurriculum().equals(ranger2.getCurriculum()));
		Assert.isTrue(ranger1.getTrips().equals(ranger2.getTrips()));

		this.unauthenticate();
	}

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

	@Test
	public void testFindAllSuspicious() {
		// REVISAR !!!
		// Cómo se comprueba que el findAll() funciona correctamente?

		final Integer currentNumberOfSuspiciousRangersInTheXML = 3;

		this.authenticate("admin1");

		final Collection<Ranger> rangers = this.rangerService.findAllSuspicious();

		Assert.notNull(rangers);
		Assert.isTrue(rangers.size() == currentNumberOfSuspiciousRangersInTheXML);

		this.unauthenticate();
	}

}
