
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorService {

	// Managed repository ------------------

	@Autowired
	private SponsorRepository	sponsorRepository;

	// Supporting services -----------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;


	// Constructors ------------------------

	public SponsorService() {
		super();
	}

	// Simple CRUD methods -----------------

	public Sponsor create(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		// REVISAR !!!
		// Pasar la UserAccount por parámetros?

		Assert.isTrue(userAccount.getAuthorities().isEmpty() || userAccount.getAuthorities().contains(Authority.SPONSOR));
		if (userAccount.getAuthorities().isEmpty()) {
			final Authority auth = new Authority();
			auth.setAuthority(Authority.SPONSOR);
			userAccount.getAuthorities().add(auth);
		}

		final Sponsor sponsor = (Sponsor) this.actorService.create(userAccount, Sponsor.class);

		final Collection<Sponsorship> sponsorships = new ArrayList<Sponsorship>();

		sponsor.setSponsorships(sponsorships);

		return sponsor;
	}

	public Collection<Sponsor> findAll() {
		Collection<Sponsor> sponsors;

		Assert.notNull(this.sponsorRepository);
		sponsors = this.sponsorRepository.findAll();
		Assert.notNull(sponsors);

		return sponsors;
	}

	public Sponsor findOne(final int sponsorId) {
		// REVISAR !!!
		// Debe tener algún assert?
		Sponsor sponsor;

		sponsor = this.sponsorRepository.findOne(sponsorId);

		return sponsor;
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		Assert.isTrue(sponsor.getUserAccount().equals(LoginService.getPrincipal()));

		// REVISAR !!!
		// Si el sponsor tiene sponsorships, debo setear el sponsor de esa sponsorship como el sponsor actual?  

		final Sponsor sponsorS = this.sponsorRepository.save(sponsor);

		if (sponsor.getId() == 0)
			this.folderService.createSystemFolders(sponsorS);

		return sponsorS;
	}

	// REVISAR !!!
	// Es necesario hacer el delete?

	// Other business methods --------------

	public Sponsor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		final Sponsor sponsor = this.sponsorRepository.findByUserAccountId(userAccount.getId());

		return sponsor;
	}

}
