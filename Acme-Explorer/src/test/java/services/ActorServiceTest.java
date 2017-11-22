
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
import domain.Actor;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private ActorService	actorService;


	// Supporting services -----------------

	// Tests -------------------------------

	@Test
	public void testCreate() {
		Actor actor;

		this.authenticate("admin1");

		final UserAccount userAccount = new UserAccount();
		final Class<Ranger> clase = Ranger.class;

		actor = this.actorService.create(userAccount, clase);

		Assert.isNull(actor.getName());
		Assert.isNull(actor.getSurname());
		Assert.isNull(actor.getEmail());
		Assert.isNull(actor.getPhoneNumber());
		Assert.isNull(actor.getAddress());
		Assert.isTrue(!actor.getIsSuspicious());
		Assert.isTrue(!actor.getIsBanned());

		Assert.notNull(actor.getSocialIDs());
		Assert.isTrue(actor.getSocialIDs().isEmpty());
		Assert.notNull(actor.getFolders());
		Assert.isTrue(!actor.getFolders().isEmpty());
		// Comprobar que el método para crear las systemFolders funciona correctamente?
		Assert.notNull(actor.getSentMessages());
		Assert.isTrue(actor.getSentMessages().isEmpty());
		Assert.notNull(actor.getReceivedMessages());
		Assert.isTrue(actor.getReceivedMessages().isEmpty());
		Assert.notNull(actor.getUserAccount());

		this.unauthenticate();
	}

	@Test
	public void testFindAll() {
		// REVISAR !!!
		// Cómo se comprueba que el findAll() funciona correctamente?

		final Integer currentNumberOfActorsInTheXML = 15;

		this.authenticate("admin1");

		final Collection<Actor> actors = this.actorService.findAll();

		Assert.notNull(actors);
		Assert.isTrue(actors.size() == currentNumberOfActorsInTheXML);

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {
		Actor actor1 = null;
		Actor actor2 = null;

		this.authenticate("admin1");

		final Collection<Actor> actors = this.actorService.findAll();

		for (final Actor a : actors)
			if (a != null) {
				actor1 = a;
				break;
			}

		actor2 = this.actorService.findOne(actor1.getId());

		Assert.isTrue(actor1.equals(actor2));

		this.unauthenticate();
	}

	@Test
	public void testFindByUserAccount() {
		Actor actor1 = null;
		Actor actor2 = null;

		this.authenticate("admin1");

		final Collection<Actor> actors = this.actorService.findAll();

		for (final Actor a : actors)
			if (a != null) {
				actor1 = a;
				break;
			}

		actor2 = this.actorService.findByUserAccount(actor1.getUserAccount());

		Assert.isTrue(actor1.equals(actor2));

		this.unauthenticate();

	}

	@Test
	public void testBan() {
		Actor actor1 = null;
		Actor actor2 = null;

		this.authenticate("admin1");

		final Collection<Actor> actors = this.actorService.findAll();
		for (final Actor a : actors)
			if (a != null && a.getIsSuspicious() == true)
				actor1 = a;

		actor2 = this.actorService.ban(actor1);

		Assert.notNull(actor2);
		Assert.isTrue(this.actorService.findOne(actor2.getId()).getIsBanned());

		this.unauthenticate();
	}

	@Test
	public void testUnban() {
		Actor actor1 = null;
		Actor actor2 = null;

		this.authenticate("admin1");

		final Collection<Actor> actors = this.actorService.findAll();
		for (final Actor a : actors)
			if (a != null && a.getIsBanned() == true)
				actor1 = a;

		actor2 = this.actorService.unban(actor1);

		Assert.notNull(actor2);
		Assert.isTrue(!this.actorService.findOne(actor2.getId()).getIsBanned());

		this.unauthenticate();
	}

}
