
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import security.LoginService;
import security.UserAccount;
import domain.Explorer;
import domain.Finder;

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
		final UserAccount userAccount = LoginService.getPrincipal();

		finder = new Finder();

		finder.setCacheTime(1);

		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);
		explorer.setFinder(finder);

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
		UserAccount userAccount;
		Explorer explorer;

		userAccount = LoginService.getPrincipal();
		explorer = this.explorerService.findByUserAccount(userAccount);

		Assert.notNull(finder);
		Assert.isTrue(finder.getId() == explorer.getFinder().getId());

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
