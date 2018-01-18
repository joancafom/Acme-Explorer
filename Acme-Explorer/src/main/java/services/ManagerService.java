
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.Authority;
import security.UserAccount;
import domain.Manager;
import domain.Ranger;
import domain.SurvivalClass;
import domain.Trip;

@Service
@Transactional
public class ManagerService {

	//Managed Repository
	@Autowired
	private ManagerRepository	managerRepository;

	//Supporting Services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;


	//Simple CRUD methods
	public Manager create(final UserAccount userAccount) {

		Assert.notNull(userAccount);

		// REVISAR !!!
		// Pasar la UserAccount por parámetros?

		Assert.isTrue(userAccount.getAuthorities().isEmpty() || userAccount.getAuthorities().contains(Authority.MANAGER));
		if (userAccount.getAuthorities().isEmpty()) {
			final Authority auth = new Authority();
			auth.setAuthority(Authority.MANAGER);
			userAccount.getAuthorities().add(auth);
		}

		final Manager res = (Manager) this.actorService.create(userAccount, Manager.class);

		res.setTrips(new ArrayList<Trip>());
		res.setSurvivalClasses(new ArrayList<SurvivalClass>());

		return res;

	}

	public Manager findOne(final int managerId) {

		return this.managerRepository.findOne(managerId);
	}

	public Collection<Manager> findAll() {

		return this.managerRepository.findAll();
	}

	public Manager save(final Manager m) {

		Assert.notNull(m);
		final Manager manager = this.managerRepository.save(m);

		if (m.getId() == 0)
			this.folderService.createSystemFolders(manager);

		return manager;
	}

	// REVISAR !!!
	// Es necesario hacer el delete?

	//Other Business operations

	public Collection<Manager> findAllSuspicious() {

		return this.managerRepository.listSuspicious();
	}

	public Manager findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		final Manager res = this.managerRepository.findByUserAccountId(userAccount.getId());

		return res;
	}

	public Boolean rangerHasManagerTrips(final Ranger ranger, final Manager manager) {
		Boolean res = false;

		for (final Trip t : manager.getTrips())
			if (t.getRanger().equals(ranger)) {
				res = true;
				break;
			}
		return res;
	}

}
