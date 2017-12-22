
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import security.LoginService;
import security.UserAccount;
import domain.Explorer;
import domain.Finder;
import domain.Trip;

@Service
@Transactional
public class FinderService {

	// Managed repository ------------------

	@Autowired
	private FinderRepository	finderRepository;

	// Supporting services -----------------

	@Autowired
	private ExplorerService		explorerService;


	// Constructors ------------------------

	public FinderService() {
		super();
	}

	// Simple CRUD methods -----------------

	public Finder create() {
		Finder finder;
		final Collection<Trip> cache = new HashSet<Trip>();
		final Date now = new Date();

		finder = new Finder();
		finder.setCache(cache);
		finder.setCacheTime(now);

		return finder;
	}

	public Collection<Finder> findAll() {
		final Collection<Finder> finders;

		Assert.notNull(this.finderRepository);
		finders = this.finderRepository.findAll();
		Assert.notNull(finders);

		return finders;
	}

	public Finder findOne(final int finderId) {
		// REVISAR !!!
		// Debe tener algún assert?
		Finder finder;

		finder = this.finderRepository.findOne(finderId);

		return finder;
	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);

		Explorer explorer = null;

		for (final Explorer e : this.explorerService.findAll())
			if (e.getFinder().equals(finder))
				explorer = e;

		if (explorer != null)
			Assert.isTrue(LoginService.getPrincipal().equals(explorer.getUserAccount()));

		Assert.isTrue((finder.getKeyword() == null && finder.getMinRange() == null && finder.getMaxRange() == null && finder.getMinDate() == null && finder.getMaxDate() == null)
			|| (finder.getKeyword() != null && finder.getMinRange() == null && finder.getMaxRange() == null && finder.getMinDate() == null && finder.getMaxDate() == null)
			|| (finder.getKeyword() != null && finder.getMinRange() != null && finder.getMaxRange() != null && finder.getMinDate() == null && finder.getMaxDate() == null)
			|| (finder.getKeyword() != null && finder.getMinRange() == null && finder.getMaxRange() == null && finder.getMinDate() != null && finder.getMaxDate() != null)
			|| (finder.getKeyword() != null && finder.getMinRange() != null && finder.getMaxRange() != null && finder.getMinDate() != null && finder.getMaxDate() != null));

		if (finder.getMinRange() != null && finder.getMaxRange() != null)
			Assert.isTrue(finder.getMinRange() < finder.getMaxRange());

		if (finder.getMinDate() != null && finder.getMaxDate() != null)
			Assert.isTrue(finder.getMinDate().before(finder.getMaxDate()));

		return this.finderRepository.save(finder);
	}
	// REVISAR !!!
	// Es necesario hacer el delete?

	// Other business methods --------------

	public Finder findByPrincipal() {
		Finder finder;

		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);

		Assert.notNull(this.finderRepository);
		finder = this.finderRepository.findAllByExplorerId(explorer.getId());
		Assert.notNull(finder);

		return finder;
	}

}
