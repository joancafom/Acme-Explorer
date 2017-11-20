
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	//Service under test
	@Autowired
	private ActorService	actorService;

	//Other required Services
	@Autowired
	private RangerService	rangerService;

	//Working Variables
	private Ranger			ranger1;


	@Before
	public void setUpWorkingVariables() {

		this.authenticate("ranger1");

		this.ranger1 = this.rangerService.findByUserAccount(LoginService.getPrincipal());

		this.unauthenticate();
	}

	@Test
	public void testFindAll() {

		this.authenticate("admin1");

		final Collection<Actor> result = this.actorService.findAll();

		//In the XML, there are 15 DISTICT actors

		Assert.notNull(result);
		Assert.isTrue(!result.isEmpty());
		Assert.isTrue(result.size() == 15);

		this.unauthenticate();

	}

	@Test
	public void testFindByUserAccount() {

		this.authenticate("ranger1");

		final Actor actorFound = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final Ranger ranger = this.rangerService.findByUserAccount(LoginService.getPrincipal());

		Assert.notNull(actorFound);
		Assert.isTrue(actorFound.equals(ranger));

		this.unauthenticate();

	}

	@Test
	public void testBan() {

		this.authenticate("admin1");

		this.actorService.ban(this.ranger1);

		Assert.notNull(this.ranger1);
		Assert.isTrue(this.rangerService.findByUserAccount(this.ranger1.getUserAccount()).getIsBanned());

		this.unauthenticate();

	}

	@Test
	public void testUnban() {

		this.authenticate("admin1");

		this.actorService.ban(this.ranger1);

		this.actorService.unban(this.ranger1);

		Assert.notNull(this.ranger1);
		Assert.isTrue(!this.rangerService.findByUserAccount(this.ranger1.getUserAccount()).getIsBanned());

		this.unauthenticate();

	}
}
