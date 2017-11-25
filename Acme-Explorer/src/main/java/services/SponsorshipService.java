
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import security.LoginService;
import security.UserAccount;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Trip;

@Service
@Transactional
public class SponsorshipService {

	//Managed Repository
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	//Supporting Services
	@Autowired
	private SponsorService			sponsorService;


	//Simple CRUD Operations

	public Sponsorship create() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Sponsor sponsor = this.sponsorService.findByUserAccount(userAccount);

		final Sponsorship res = new Sponsorship();

		res.setSponsor(sponsor);
		sponsor.getSponsorships().add(res);

		return res;
	}

	public Sponsorship findOne(final int sponsorshipId) {

		return this.sponsorshipRepository.findOne(sponsorshipId);
	}

	public Collection<Sponsorship> findAll() {

		return this.sponsorshipRepository.findAll();
	}

	public Sponsorship save(final Sponsorship ss) {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Sponsor sponsor = this.sponsorService.findByUserAccount(userAccount);

		Assert.notNull(ss);
		Assert.isTrue(sponsor.equals(ss.getSponsor()));

		return this.sponsorshipRepository.save(ss);
	}

	public void delete(final Sponsorship sponsorship) {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Sponsor sponsor = this.sponsorService.findByUserAccount(userAccount);

		Assert.notNull(sponsorship);
		Assert.notNull(sponsor);
		Assert.isTrue(sponsorship.getSponsor().equals(sponsor));

		this.sponsorshipRepository.delete(sponsorship.getId());
	}
	//Other Business methods

	public Collection<Sponsorship> findByTrip(final Trip trip) {
		Assert.notNull(trip);

		return this.sponsorshipRepository.findByTripId(trip.getId());
	}
}
