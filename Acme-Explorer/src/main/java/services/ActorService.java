
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Explorer;
import domain.Ranger;

@Service
@Transactional
public class ActorService {

	// Managed repository ------------------
	//Hola
	//Adiós
	@Autowired
	private ActorRepository	actorRepository;

	// Supporting services -----------------

	@Autowired
	private RangerService	rangerService;

	@Autowired
	private ExplorerService	explorerService;


	// Constructors ------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods -----------------

	public Collection<Actor> findAll() {
		Collection<Actor> actors;

		actors = this.actorRepository.findAll();
		Assert.notNull(actors);

		return actors;
	}

	public Actor save(final Actor actor) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().equals(userAccount));

		return this.actorRepository.save(actor);
	}

	// Other business methods --------------

	public void registerAsARanger() {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.isNull(userAccount);

		Ranger ranger;

		ranger = this.rangerService.create();

		this.rangerService.save(ranger);
	}

	public void registerAsAnExplorer() {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.isNull(userAccount);

		Explorer explorer;

		explorer = this.explorerService.create();

		this.explorerService.save(explorer);
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Actor actor;

		actor = this.actorRepository.findByUserAccountId(userAccount.getId());

		return actor;
	}

	public void ban(final Actor actor) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.isTrue(!this.actorRepository.findByUserAccountId(userAccount.getId()).equals(userAccount.getId()));

		Assert.notNull(actor);
		Assert.isTrue(actor.getIsSuspicious());

		actor.setIsBanned(true);
	}

	public void unban(final Actor actor) {
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.isTrue(!this.actorRepository.findByUserAccountId(userAccount.getId()).equals(userAccount.getId()));

		Assert.notNull(actor);

		actor.setIsBanned(false);
	}

}
