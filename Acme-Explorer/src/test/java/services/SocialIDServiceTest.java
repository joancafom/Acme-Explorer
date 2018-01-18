
package services;

import java.util.Collection;
import java.util.LinkedList;
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
import utilities.AbstractTest;
import domain.Actor;
import domain.Explorer;
import domain.SocialID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SocialIDServiceTest extends AbstractTest {

	@Autowired
	private SocialIDService	socialIDService;
	@Autowired
	private ExplorerService	explorerService;
	@Autowired
	private ActorService	actorService;


	@Test
	public void testCreate() {
		super.authenticate("explorer1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);

		final SocialID socialID = this.socialIDService.create();
		Assert.notNull(socialID);
		Assert.isTrue(socialID.getActor().equals(explorer));

		this.unauthenticate();
	}

	@Test
	public void testFindAll() {
		super.authenticate("admin1");
		final Collection<SocialID> socialIDs = new LinkedList<SocialID>();
		for (final Actor a : this.actorService.findAll())
			socialIDs.addAll(a.getSocialIDs());

		Assert.isTrue(socialIDs.containsAll(this.socialIDService.findAll()) && socialIDs.size() == this.socialIDService.findAll().size());

		this.unauthenticate();
	}

	@Test
	public void testSave() {
		super.authenticate("explorer1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);

		final SocialID socialID = this.socialIDService.create();
		this.socialIDService.save(socialID);
		Assert.isTrue(explorer.getSocialIDs().contains(socialID));
		this.unauthenticate();
	}

	@Test
	public void testDelete() {
		super.authenticate("admin1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor explorer = this.actorService.findByUserAccount(userAccount);

		final List<SocialID> socialIDs = new LinkedList<SocialID>(explorer.getSocialIDs());
		final SocialID toRemove = socialIDs.get(0);

		this.socialIDService.delete(toRemove);
		this.unauthenticate();

	}

	@Test
	public void testFindOne() {
		super.authenticate("admin1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor explorer = this.actorService.findByUserAccount(userAccount);

		final List<SocialID> socialIDs = new LinkedList<SocialID>(explorer.getSocialIDs());
		final SocialID socialID = socialIDs.get(0);

		Assert.isTrue(socialID.equals(this.socialIDService.findOne(socialID.getId())));
	}

}
