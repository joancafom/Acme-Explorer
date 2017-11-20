
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
import security.UserAccountRepository;
import utilities.AbstractTest;
import domain.Auditor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AuditorServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private AuditorService			auditorService;

	// Supporting repositories -------------

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Supporting services -----------------

	// Tests -------------------------------

	@Test
	public void testFindByUserAccount() {
		this.authenticate("auditor1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Auditor auditor = this.auditorService.findByUserAccount(this.userAccountRepository.findByUsername("auditor1"));

		Assert.notNull(auditor);
		Assert.isTrue(userAccount.equals(auditor.getUserAccount()));

		this.unauthenticate();
	}
}
