
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import security.LoginService;
import utilities.AbstractTest;
import domain.Curriculum;
import domain.PersonalRecord;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private PersonalRecordService	personalRecordService;

	// Supporting repositories -------------

	@Autowired
	private CurriculumRepository	curriculumRepository;

	// Supporting services -----------------

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private RangerService			rangerService;


	// Tests -------------------------------

	@Test
	public void testCreate() {
		PersonalRecord personalRecord;
		Curriculum curriculum;

		this.authenticate("ranger1");

		curriculum = this.curriculumService.findByActualRanger();

		personalRecord = this.personalRecordService.create(curriculum);

		Assert.isNull(personalRecord.getFullName());
		Assert.isNull(personalRecord.getPhoto());
		Assert.isNull(personalRecord.getEmail());
		Assert.isNull(personalRecord.getPhoneNumber());
		Assert.isNull(personalRecord.getLinkedInProfile());

		Assert.notNull(personalRecord.getCurriculum());
		Assert.isTrue(personalRecord.getCurriculum().equals(curriculum));

		this.unauthenticate();
	}

	@Test
	public void testSave() {
		Ranger ranger;
		PersonalRecord personalRecord = null;

		this.authenticate("ranger1");

		ranger = this.rangerService.findByUserAccount(LoginService.getPrincipal());

		personalRecord = ranger.getCurriculum().getPersonalRecord();

		final String fullName = "Full name";
		final String photo = "http://www.photo.com";
		final String email = "email@gmail.com";
		final String phoneNumber = "954628463";
		final String linkedInProfile = "http://www.linkedInProfile.com";

		personalRecord.setFullName(fullName);
		personalRecord.setPhoto(photo);
		personalRecord.setEmail(email);
		personalRecord.setPhoneNumber(phoneNumber);
		personalRecord.setLinkedInProfile(linkedInProfile);

		final PersonalRecord personalRecordS = this.personalRecordService.save(personalRecord);

		Assert.isTrue(personalRecordS.getFullName().equals(fullName));
		Assert.isTrue(personalRecordS.getPhoto().equals(photo));
		Assert.isTrue(personalRecordS.getEmail().equals(email));
		Assert.isTrue(personalRecordS.getPhoneNumber().equals(phoneNumber));
		Assert.isTrue(personalRecordS.getLinkedInProfile().equals(linkedInProfile));

		this.unauthenticate();
	}

	@Test
	public void testFindByCurriculum() {
		Curriculum curriculum = null;
		Collection<Curriculum> curriculums;
		PersonalRecord personalRecord;

		this.authenticate("ranger1");

		curriculums = this.curriculumRepository.findAll();

		for (final Curriculum c : curriculums) {
			curriculum = c;
			break;
		}

		personalRecord = this.personalRecordService.findByCurriculum(curriculum);

		Assert.isTrue(personalRecord.equals(curriculum.getPersonalRecord()));

		this.unauthenticate();
	}
}
