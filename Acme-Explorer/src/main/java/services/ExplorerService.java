
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ExplorerRepository;
import security.Authority;
import security.UserAccount;
import domain.Contact;
import domain.Explorer;
import domain.Finder;
import domain.Story;
import domain.SurvivalClass;
import domain.TripApplication;

@Service
@Transactional
public class ExplorerService {

	/* Repository */
	@Autowired
	private ExplorerRepository	explorerRepository;

	/* Services */
	@Autowired
	private ActorService		actorService;


	public Explorer create(final UserAccount userAccount, final Finder finder) {

		Assert.notNull(userAccount);

		// REVISAR !!!
		// Pasar la UserAccount por parámetros?

		Assert.isTrue(userAccount.getAuthorities().isEmpty() || userAccount.getAuthorities().contains(Authority.EXPLORER));
		if (userAccount.getAuthorities().isEmpty()) {
			final Authority auth = new Authority();
			auth.setAuthority(Authority.EXPLORER);
			userAccount.getAuthorities().add(auth);
		}

		final Explorer explorer = (Explorer) this.actorService.create(userAccount, Explorer.class);

		explorer.setStories(new ArrayList<Story>());
		explorer.setSurvivalClasses(new ArrayList<SurvivalClass>());
		explorer.setTripApplications(new ArrayList<TripApplication>());
		explorer.setEmergencyContacts(new ArrayList<Contact>());
		explorer.setFinder(finder);

		return explorer;
	}
	public Collection<Explorer> findAll() {
		return this.explorerRepository.findAll();
	}

	public Explorer findOne(final int id) {
		return this.explorerRepository.findOne(id);
	}

	public Explorer save(final Explorer explorer) {
		Assert.notNull(explorer);

		return this.explorerRepository.save(explorer);
	}
	public Explorer findByUserAccount(final UserAccount userAccount) {

		Assert.notNull(userAccount);
		final Explorer explorer = this.explorerRepository.findByUserAccountId(userAccount.getId());

		return explorer;
	}

}
