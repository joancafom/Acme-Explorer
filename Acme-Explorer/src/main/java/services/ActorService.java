
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.UserAccount;
import domain.Actor;
import domain.Folder;
import domain.Message;
import domain.SocialID;

@Service
@Transactional
public class ActorService {

	// Managed repository ------------------

	@Autowired
	private ActorRepository	actorRepository;

	// Supporting services -----------------

	@Autowired
	private FolderService	folderService;


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
		final Collection<Folder> folders;
		final Collection<Message> sentMessages = new ArrayList<Message>();
		final Collection<Message> receivedMessages = new ArrayList<Message>();

		try {
			actor = clase.newInstance();
		} catch (final InstantiationException e) {
		} catch (final IllegalAccessException e) {
		}

		folders = this.folderService.createSystemFolders(actor);

		actor.setIsSuspicious(false);
		actor.setIsBanned(false);

		actor.setSocialIDs(socialIDs);
		actor.setFolders(folders);
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

	//
	//	public void ban(final Actor actor) {
	//		UserAccount userAccount;
	//
	//		userAccount = LoginService.getPrincipal();
	//		Assert.isTrue(!this.actorRepository.findByUserAccountId(userAccount.getId()).equals(userAccount.getId()));
	//
	//		Assert.notNull(actor);
	//		Assert.isTrue(actor.getIsSuspicious());
	//
	//		actor.setIsBanned(true);
	//	}
	//
	//	public void unban(final Actor actor) {
	//		UserAccount userAccount;
	//
	//		userAccount = LoginService.getPrincipal();
	//		Assert.isTrue(!this.actorRepository.findByUserAccountId(userAccount.getId()).equals(userAccount.getId()));
	//
	//		Assert.notNull(actor);
	//
	//		actor.setIsBanned(false);
	//	}

}
