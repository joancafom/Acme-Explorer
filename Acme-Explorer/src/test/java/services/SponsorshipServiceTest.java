
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.TripRepository;
import utilities.AbstractTest;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	private SponsorshipService	sponsorshipService;
	@Autowired
	private TripRepository		tripRepository;


	@Test
	public void testFindByTrip() {

		final List<Trip> allTrips = this.tripRepository.findAll();
		final Trip trip = allTrips.get(0);

		Assert.isTrue(trip.getSponsorships().containsAll(this.sponsorshipService.findByTrip(trip)) && trip.getSponsorships().size() == this.sponsorshipService.findByTrip(trip).size());
		this.unauthenticate();

	}

}
