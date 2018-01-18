
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Message;
import domain.SocialID;

@Service
@Transactional
public class ActorService {

	// Managed repository ------------------

	@Autowired
	private ActorRepository	actorRepository;


	// Constructors ------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods -----------------

	public Actor create(final UserAccount userAccount, final Class<? extends Actor> clase) {
		Assert.notNull(userAccount);
		Assert.notNull(clase);

		// REVISAR !!!
		// Pasar la UserAccount por parámetros?
		// Assert.isTrue(userAccount.getAuthorities().contains(clase.toString()));

		Actor actor = null;
		final Collection<SocialID> socialIDs = new ArrayList<SocialID>();
		final Collection<Message> sentMessages = new ArrayList<Message>();
		final Collection<Message> receivedMessages = new ArrayList<Message>();

		try {
			actor = clase.newInstance();
		} catch (final InstantiationException e) {
		} catch (final IllegalAccessException e) {
		}

		actor.setIsSuspicious(false);

		actor.setSocialIDs(socialIDs);
		actor.setSentMessages(sentMessages);
		actor.setReceivedMessages(receivedMessages);
		actor.setUserAccount(userAccount);

		return actor;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> actors;

		Assert.notNull(this.actorRepository);
		actors = this.actorRepository.findAll();
		Assert.notNull(actors);

		return actors;
	}

	public Actor findOne(final int actorId) {
		// REVISAR !!!
		// Debe tener algún assert?
		Actor actor;

		actor = this.actorRepository.findOne(actorId);

		return actor;
	}

	// REVISAR !!!
	// Tiene sentido hacer un método save de Actor?
	// Tiene sentido hacer un método delete de Actor?

	// Other business methods --------------

	public Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		final Actor actor = this.actorRepository.findByUserAccountId(userAccount.getId());

		return actor;
	}

	public Actor ban(final Actor actor) {
		Assert.notNull(actor);

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(!this.actorRepository.findByUserAccountId(userAccount.getId()).equals(actor));

		Assert.isTrue(actor.getIsSuspicious());

		actor.getUserAccount().setIsLocked(true);
		return this.actorRepository.save(actor);
	}

	public Actor unban(final Actor actor) {
		Assert.notNull(actor);

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(!this.actorRepository.findByUserAccountId(userAccount.getId()).equals(actor));

		Assert.isTrue(actor.getIsSuspicious());

		actor.getUserAccount().setIsLocked(false);
		return this.actorRepository.save(actor);
	}

}
