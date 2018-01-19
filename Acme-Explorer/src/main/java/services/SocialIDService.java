
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialIDRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.SocialID;

@Service
@Transactional
public class SocialIDService {

	//Managed Repository
	@Autowired
	private SocialIDRepository	socialIDRepository;

	//External Services
	@Autowired
	private ActorService		actorService;


	public SocialID create() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Actor actor = this.actorService.findByUserAccount(userAccount);

		final SocialID socialID = new SocialID();
		socialID.setActor(actor);

		actor.getSocialIDs().add(socialID);

		return socialID;
	}

	public Collection<SocialID> findAll() {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<SocialID> socialIDs = this.socialIDRepository.findAll();
		return socialIDs;
	}

	public SocialID save(final SocialID socialID) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		return this.socialIDRepository.save(socialID);
	}

	public void delete(final SocialID socialID) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		this.socialIDRepository.delete(socialID);
	}

	public SocialID findOne(final int id) {
		return this.socialIDRepository.findOne(id);
	}
}
