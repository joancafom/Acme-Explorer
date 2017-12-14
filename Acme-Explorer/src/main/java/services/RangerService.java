
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RangerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Ranger;
import domain.Trip;

@Service
@Transactional
public class RangerService {

	// Managed repository ------------------

	@Autowired
	private RangerRepository	rangerRepository;

	// Supporting services -----------------

	@Autowired
	private ActorService		actorService;


	// Constructors ------------------------

	public RangerService() {
		super();
	}

	// Simple CRUD methods -----------------

	public Ranger create(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		// REVISAR !!!
		// Pasar la UserAccount por parámetros?

		Assert.isTrue(userAccount.getAuthorities().isEmpty() || userAccount.getAuthorities().contains(Authority.RANGER));
		if (userAccount.getAuthorities().isEmpty()) {
			final Authority auth = new Authority();
			auth.setAuthority(Authority.RANGER);
			userAccount.getAuthorities().add(auth);
		}

		final Ranger ranger = (Ranger) this.actorService.create(userAccount, Ranger.class);

		final Collection<Trip> trips = new ArrayList<Trip>();

		ranger.setTrips(trips);

		return ranger;
	}

	public Collection<Ranger> findAll() {
		Collection<Ranger> rangers;

		Assert.notNull(this.rangerRepository);
		rangers = this.rangerRepository.findAll();
		Assert.notNull(rangers);

		return rangers;
	}

	public Ranger findOne(final int rangerId) {
		// REVISAR !!!
		// Debe tener algún assert?
		Ranger ranger;

		ranger = this.rangerRepository.findOne(rangerId);

		return ranger;
	}

	public Ranger save(final Ranger ranger) {
		Assert.notNull(ranger);
		//An anonymous user can create!
		//LoginService when anonymous throws an exception!
		Assert.isTrue(ranger.getId() == 0 || ranger.getUserAccount().equals(LoginService.getPrincipal()));

		// REVISAR !!!
		// Si el ranger tiene currículum, debo setear el ranger de ese curriculum como el ranger actual?

		return this.rangerRepository.save(ranger);
	}

	// REVISAR !!!
	// Es necesario hacer el delete?

	// Other business methods --------------

	public Ranger findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		final Ranger ranger = this.rangerRepository.findByUserAccountId(userAccount.getId());

		return ranger;
	}

	public Collection<Ranger> findAllSuspicious() {
		Collection<Ranger> rangers;

		Assert.notNull(this.rangerRepository);
		rangers = this.rangerRepository.findAllSuspicious();
		Assert.notNull(rangers);

		return rangers;
	}
}
