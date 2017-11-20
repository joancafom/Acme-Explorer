
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
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	@Autowired
	private SponsorService	sponsorService;
	@Autowired
	private ActorService	actorService;


	@Test
	public void testSave() {
		super.authenticate("sponsor1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Sponsor sponsor = this.sponsorService.findByUserAccount(userAccount);
		this.sponsorService.save(sponsor);
		Assert.isTrue(this.actorService.findAll().contains(sponsor));
		this.unauthenticate();
	}

	@Test
	public void testFindByUserAccount() {
		super.authenticate("sponsor1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Sponsor s = this.sponsorService.findByUserAccount(userAccount);
		Assert.isTrue(s.getUserAccount().getUsername().equals("sponsor1"));
		this.unauthenticate();

	}
}
