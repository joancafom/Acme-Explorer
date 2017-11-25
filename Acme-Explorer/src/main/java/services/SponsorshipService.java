
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Sponsorship;
import domain.Trip;

@Service
@Transactional
public class SponsorshipService {

	//Managed Repository
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;


	//Simple CRUD Operations

	//Other Business methods

	public Collection<Sponsorship> findByTrip(final Trip trip) {
		Assert.notNull(trip);

		return this.sponsorshipRepository.findByTripId(trip.getId());
	}
}
