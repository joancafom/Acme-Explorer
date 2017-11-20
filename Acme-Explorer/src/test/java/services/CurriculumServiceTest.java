
package services;

import java.util.ArrayList;
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
import domain.Curriculum;
import domain.Ranger;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	@Autowired
	private CurriculumService	curriculumService;
	@Autowired
	private RangerService		rangerService;
	@Autowired
	private TripRepository		tripRepository;


	//	@Test
	//	public void testCreate(){
	//		
	//		super.authenticate("ranger3");
	//		UserAccount userAccount = LoginService.getPrincipal();
	//		Ranger ranger = this.rangerService.findByUserAccount(userAccount);
	//		Personal
	//		Curriculum curriculum= new Curriculum();
	//		PersonalRecord personalRecord = this.personalRecordService.create(curriculum);
	//		curriculum = this.curriculumService.create(personalRecord);
	//		
	//		Assert.notNull(curriculum);
	//		
	//		
	//	}

	@Test
	public void testFindOne() {
		super.authenticate("ranger1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);
		final Curriculum curriculum = ranger.getCurriculum();

		Assert.isTrue(curriculum.equals(this.curriculumService.findOne(curriculum.getId())));
		this.unauthenticate();
	}

	@Test
	public void testSave() {
		super.authenticate("ranger1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);
		final Curriculum curriculum = ranger.getCurriculum();
		curriculum.getPersonalRecord().setFullName("Lolita Flores");
		this.curriculumService.save(curriculum);
		Assert.isTrue(this.curriculumService.findByActualRanger().equals(curriculum));
		Assert.isTrue(this.curriculumService.findByActualRanger().getPersonalRecord().getFullName().equals("Lolita Flores"));
		this.unauthenticate();
	}

	@Test
	public void testDelete() {
		super.authenticate("ranger1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);
		final Curriculum curriculum = ranger.getCurriculum();
		//System.out.println(ranger.getCurriculum());
		this.curriculumService.delete(curriculum);
		this.rangerService.save(ranger);
		//System.out.println(ranger.getCurriculum());
		this.unauthenticate();
	}

	@Test
	public void testFindByTrip() {

		final List<Trip> trips = new ArrayList<Trip>(this.tripRepository.findAll());

		final Trip rTrip = trips.get(2);
		Assert.isTrue(rTrip.getRanger().getCurriculum().equals(this.curriculumService.findByTrip(rTrip)));
		this.unauthenticate();

	}

	@Test
	public void testFindByActualRanger() {
		super.authenticate("ranger1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		Assert.isTrue(ranger.getCurriculum().equals(this.curriculumService.findByActualRanger()));
		this.unauthenticate();
	}

	@Test
	public void testFindByRanger() {
		super.authenticate("ranger1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);
		super.authenticate(null);
		Assert.isTrue(ranger.getCurriculum().equals(this.curriculumService.findByRanger(ranger)));
		this.unauthenticate();
	}

}
