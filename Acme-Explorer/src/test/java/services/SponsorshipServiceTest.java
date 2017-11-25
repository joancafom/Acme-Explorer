
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.TripRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;
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

	@Autowired
	private SponsorService		sponsorService;


	@Test
	public void testCreate() {

		this.authenticate("sponsor1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Sponsor sponsor = this.sponsorService.findByUserAccount(userAccount);

		final Sponsorship testSponsorship = this.sponsorshipService.create();

		Assert.notNull(testSponsorship);
		Assert.notNull(testSponsorship.getSponsor());
		Assert.isNull(testSponsorship.getBannerUrl());
		Assert.isNull(testSponsorship.getInfoPageLink());
		Assert.isNull(testSponsorship.getCreditCard());
		Assert.isNull(testSponsorship.getTrip());
		Assert.isTrue(sponsor.equals(testSponsorship.getSponsor()));

		this.unauthenticate();

	}

	@Test
	public void testFindOne() {

		this.authenticate("sponsor1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Sponsor sponsor = this.sponsorService.findByUserAccount(userAccount);

		final List<Sponsorship> sponsorships = new ArrayList<Sponsorship>(sponsor.getSponsorships());
		final Sponsorship sponsorship = sponsorships.get(0);

		final Sponsorship foundSponsorship = this.sponsorshipService.findOne(sponsorship.getId());

		Assert.notNull(foundSponsorship);
		Assert.isTrue(sponsorship.equals(foundSponsorship));

		this.unauthenticate();
	}

	@Test
	public void testFindAll() {

		this.authenticate("admin1");

		final Collection<Sponsorship> allSponsorships = new HashSet<Sponsorship>();

		for (final Sponsor s : this.sponsorService.findAll())
			allSponsorships.addAll(s.getSponsorships());

		final Collection<Sponsorship> foundSponsorships = this.sponsorshipService.findAll();

		Assert.notNull(foundSponsorships);
		Assert.isTrue(allSponsorships.containsAll(foundSponsorships));
		Assert.isTrue(foundSponsorships.containsAll(allSponsorships));
		Assert.isTrue(allSponsorships.size() == foundSponsorships.size());

		this.unauthenticate();
	}

	@Test
	public void testSave() {

		this.authenticate("sponsor1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Sponsor sponsor = this.sponsorService.findByUserAccount(userAccount);

		final List<Sponsorship> sponsorships = new ArrayList<Sponsorship>(sponsor.getSponsorships());
		final Sponsorship sponsorship = sponsorships.get(0);
		final CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Visa Electron");
		creditCard.setNumber("4556481014623104");
		creditCard.setCVV(124);
		creditCard.setHolderName("Jessie J");
		creditCard.setMonth(12);
		creditCard.setYear(2025);

		sponsorship.setBannerUrl("http://benq.com/myMonitor.jpg");
		sponsorship.setCreditCard(creditCard);
		sponsorship.setInfoPageLink("http://benq.com/myMonitor");

		final Sponsorship savedSponsorship = this.sponsorshipService.save(sponsorship);

		Assert.notNull(savedSponsorship);
		Assert.isTrue(sponsorship.getBannerUrl().equals(savedSponsorship.getBannerUrl()));
		Assert.isTrue(sponsorship.getInfoPageLink().equals(savedSponsorship.getInfoPageLink()));
		Assert.isTrue(sponsorship.getSponsor().equals(savedSponsorship.getSponsor()));
		Assert.isTrue(sponsorship.getTrip().equals(savedSponsorship.getTrip()));
		Assert.isTrue(sponsorship.getCreditCard().getBrandName().equals(savedSponsorship.getCreditCard().getBrandName()));
		Assert.isTrue(sponsorship.getCreditCard().getNumber().equals(savedSponsorship.getCreditCard().getNumber()));
		Assert.isTrue(sponsorship.getCreditCard().getCVV().equals(savedSponsorship.getCreditCard().getCVV()));
		Assert.isTrue(sponsorship.getCreditCard().getHolderName().equals(savedSponsorship.getCreditCard().getHolderName()));
		Assert.isTrue(sponsorship.getCreditCard().getMonth() == savedSponsorship.getCreditCard().getMonth());
		Assert.isTrue(sponsorship.getCreditCard().getYear() == savedSponsorship.getCreditCard().getYear());
		Assert.notNull(this.sponsorshipService.findOne(savedSponsorship.getId()));
		Assert.isTrue(this.sponsorshipService.findOne(savedSponsorship.getId()).equals(savedSponsorship));

		this.unauthenticate();

	}

	@Test
	public void testDelete() {

		this.authenticate("sponsor1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Sponsor sponsor = this.sponsorService.findByUserAccount(userAccount);

		final List<Sponsorship> sponsorships = new ArrayList<Sponsorship>(sponsor.getSponsorships());
		final Sponsorship sponsorship = sponsorships.get(0);

		this.sponsorshipService.delete(sponsorship);

		Assert.isNull(this.sponsorshipService.findOne(sponsorship.getId()));

		this.unauthenticate();
	}
	@Test
	public void testFindByTrip() {

		final List<Trip> allTrips = this.tripRepository.findAll();
		final Trip trip = allTrips.get(0);

		final Collection<Sponsorship> tripSponsorships = trip.getSponsorships();
		final Collection<Sponsorship> foundSponsorships = this.sponsorshipService.findByTrip(trip);

		Assert.notNull(foundSponsorships);
		Assert.isTrue(tripSponsorships.containsAll(foundSponsorships));
		Assert.isTrue(foundSponsorships.containsAll(tripSponsorships));
		Assert.isTrue(tripSponsorships.size() == foundSponsorships.size());

		this.unauthenticate();

	}
}
