
package services;

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

	public Finder save(final Finder finder) {
		UserAccount userAccount;
		Explorer explorer;

		userAccount = LoginService.getPrincipal();

		explorer = this.explorerService.findByUserAccount(userAccount);

		Assert.isTrue(finder.getId() == explorer.getFinder().getId());

		Assert.notNull(finder);

		explorer.setFinder(finder);

		return this.finderRepository.save(finder);
	}
	// Other business methods --------------

}
