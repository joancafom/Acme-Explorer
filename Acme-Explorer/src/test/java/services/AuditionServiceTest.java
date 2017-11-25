
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.AuditionRepository;
import security.LoginService;
import security.UserAccountRepository;
import utilities.AbstractTest;
import domain.Audition;
import domain.Auditor;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AuditionServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private AuditionService			auditionService;

	// Supporting repositories -------------

	@Autowired
	private UserAccountRepository	userAccountRepository;

	@Autowired
	private AuditionRepository		auditionRepository;

	// Supporting services -----------------

	@Autowired
	private TripService				tripService;

	@Autowired
	private AuditorService			auditorService;


	// Tests -------------------------------

	@Test
	public void testCreate() {
		Audition audition;
		Collection<Trip> trips;
		Trip trip = null;
		Date currentMoment;

		trips = this.tripService.findAll();
		for (final Trip t : trips) {
			trip = t;
			break;
		}

		currentMoment = new Date();

		this.authenticate("auditor2");

		audition = this.auditionService.create(trip);

		Assert.notNull(audition.getMoment());
		Assert.isTrue(audition.getMoment().before(currentMoment));

		Assert.isNull(audition.getTitle());
		Assert.isNull(audition.getDescription());

		Assert.notNull(audition.getAttachments());
		Assert.isTrue(audition.getAttachments().isEmpty());

		Assert.notNull(audition.getIsFinal());
		Assert.isTrue(!audition.getIsFinal());

		Assert.notNull(audition.getAuditor());
		Assert.isTrue(audition.getAuditor().getUserAccount().equals(this.userAccountRepository.findByUsername("auditor2")));

		Assert.notNull(audition.getTrip());
		Assert.isTrue(audition.getTrip().equals(trip));

		this.unauthenticate();
	}

	@Test
	public void testSave() {
		Audition audition = null;
		final Collection<Audition> auditions;

		this.authenticate("auditor1");

		auditions = this.auditionRepository.findAll();

		for (final Audition a : auditions)
			if (!a.getIsFinal()) {
				audition = a;
				break;
			}

		final String title = "Title";
		final String description = "Description";
		final Collection<String> attachments = new ArrayList<String>();
		attachments.add("http://www.attachment.com");

		audition.setAttachments(attachments);
		audition.setDescription(description);
		audition.setTitle(title);

		final Audition auditionS = this.auditionService.save(audition);

		Assert.isTrue(auditionS.getAttachments().containsAll(attachments));
		Assert.isTrue(auditionS.getDescription().equals(description));
		Assert.isTrue(auditionS.getTitle().equals(title));

		this.unauthenticate();
	}

	@Test
	public void testDelete() {
		Auditor auditor;
		Audition audition = null;

		this.authenticate("auditor1");

		auditor = this.auditorService.findByUserAccount(LoginService.getPrincipal());

		for (final Audition a : auditor.getAuditions()) {
			audition = a;
			break;
		}

		this.auditionService.delete(audition);

		Assert.isTrue(this.auditionRepository.findOne(audition.getId()) == null);

	}
	
	@Test
	public void testFindByAuditor(){
		Auditor auditor;
		Collection<Audition> auditions = new HashSet<Audition>();

		this.authenticate("auditor1");

		auditor = this.auditorService.findByUserAccount(LoginService.getPrincipal());
		for(Audition a: this.auditionRepository.findAll()){
			if(a.getAuditor().equals(auditor)){
				auditions.add(a);
			}
		}
		Assert.isTrue(auditor.getAuditions().containsAll(this.auditionService.findByCurrentAuditor()) &&
				auditor.getAuditions().size()==this.auditionService.findByCurrentAuditor().size());
		
	}
	
	@Test
	public void testFindByTrip(){
		Collection<Audition> auditions;
		Trip trip = new Trip();


		for(Trip t: this.tripService.findAll()){
			trip = t;
			break;
		}
		
		
		
		auditions = new HashSet<Audition>(trip.getAuditions());
		System.out.println("auditions: "+auditions);
		System.out.println("findbyTrip: "+this.auditionService.findByTrip(trip));
		Assert.isTrue(this.auditionService.findByTrip(trip).containsAll(auditions) &&
				auditions.size()==this.auditionService.findByTrip(trip).size());
	}
}
