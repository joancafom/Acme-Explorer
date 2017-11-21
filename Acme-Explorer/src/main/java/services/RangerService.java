
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RangerRepository;
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

		return this.rangerRepository.save(ranger);
	}

	// REVISAR !!!
	// Es necesario hacer el delete?

	// Other business methods --------------

	//	public Collection<Ranger> findAllSuspicious() {
	//		Collection<Ranger> rangers;
	//
	//		rangers = this.rangerRepository.findAllSuspicious();
	//
	//		Assert.notNull(rangers);
	//
	//		return rangers;
	//	}

	public Ranger findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		final Ranger ranger = this.rangerRepository.findByUserAccountId(userAccount.getId());

		return ranger;
	}
}
