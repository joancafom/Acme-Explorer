
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.UserAccount;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	// Managed repository ------------------

	@Autowired
	private SponsorRepository	sponsorRepository;


	// Supporting services -----------------

	// Constructors ------------------------

	public SponsorService() {
		super();
	}

	// Simple CRUD methods -----------------

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);

		return this.sponsorRepository.save(sponsor);
	}

	// Other business methods --------------

	public Sponsor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		final Sponsor sponsor = this.sponsorRepository.findByUserAccountId(userAccount.getId());
		return sponsor;
	}

}
