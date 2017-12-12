
package services;

import java.util.ArrayList;
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
import domain.Sponsor;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private SponsorService	sponsorService;


	// Supporting services -----------------

	// Tests -------------------------------

	@Test
	public void testCreate() {
		final Sponsor sponsor;

		this.authenticate("admin1");

		final UserAccount userAccount = new UserAccount();

		sponsor = this.sponsorService.create(userAccount);

		Assert.notNull(sponsor.getSponsorships());
		Assert.isTrue(sponsor.getSponsorships().isEmpty());

		this.unauthenticate();
	}

	@Test
	public void testFindAll() {
		// REVISAR !!!
		// Cómo se comprueba que el findAll() funciona correctamente?

		final Integer currentNumberOfSponsorsInTheXML = 2;

		this.authenticate("admin1");

		final Collection<Sponsor> sponsors = this.sponsorService.findAll();

		Assert.notNull(sponsors);
		Assert.isTrue(sponsors.size() == currentNumberOfSponsorsInTheXML);

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {
		Sponsor sponsor1 = null;
		Sponsor sponsor2 = null;

		this.authenticate("admin1");

		final Collection<Sponsor> sponsors = this.sponsorService.findAll();

		for (final Sponsor s : sponsors)
			if (s != null) {
				sponsor1 = s;
				break;
			}

		sponsor2 = this.sponsorService.findOne(sponsor1.getId());

		Assert.isTrue(sponsor1.equals(sponsor2));

		this.unauthenticate();
	}

	@Test
	public void testSave() {
		// REVISAR !!!
		// Qué se debe comprobar en el save?

		Sponsor sponsor1 = null;
		Sponsor sponsor2 = null;

		this.authenticate("sponsor1");

		final Collection<Sponsor> sponsors = this.sponsorService.findAll();

		for (final Sponsor s : sponsors)
			if (s != null) {
				sponsor1 = s;
				break;
			}

		sponsor1.setName("Name");
		sponsor1.setSurname("Surname");
		sponsor1.setEmail("email@gmail.com");
		sponsor1.setPhoneNumber("954673648");
		sponsor1.setIsSuspicious(true);
		// REVISAR !!!
		// Se puede cambiar las sponsorships de un sponsor a otro?

		final Collection<Sponsorship> sponsorships = new ArrayList<Sponsorship>();
		sponsor1.setSponsorships(sponsorships);

		sponsor2 = this.sponsorService.save(sponsor1);

		Assert.notNull(sponsor2);
		Assert.isTrue(sponsor1.getName().equals(sponsor2.getName()));
		Assert.isTrue(sponsor1.getSurname().equals(sponsor2.getSurname()));
		Assert.isTrue(sponsor1.getEmail().equals(sponsor2.getEmail()));
		Assert.isTrue(sponsor1.getPhoneNumber().equals(sponsor2.getPhoneNumber()));
		Assert.isTrue(sponsor1.getIsSuspicious() == sponsor2.getIsSuspicious());
		Assert.isTrue(sponsor1.getSponsorships().equals(sponsor2.getSponsorships()));
	}

	@Test
	public void testFindByUserAccount() {
		Sponsor sponsor1 = null;
		Sponsor sponsor2 = null;

		this.authenticate("admin1");

		final Collection<Sponsor> sponsors = this.sponsorService.findAll();

		for (final Sponsor s : sponsors)
			if (s != null) {
				sponsor1 = s;
				break;
			}

		sponsor2 = this.sponsorService.findByUserAccount(sponsor1.getUserAccount());

		Assert.isTrue(sponsor1.equals(sponsor2));

		this.unauthenticate();

	}
}
