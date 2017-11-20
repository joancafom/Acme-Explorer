
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import repositories.EducationRecordRepository;
import security.LoginService;
import utilities.AbstractTest;
import domain.Curriculum;
import domain.EducationRecord;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private EducationRecordService		educationRecordService;

	// Supporting repositories -------------

	@Autowired
	private CurriculumRepository		curriculumRepository;

	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	// Supporting services -----------------

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private RangerService				rangerService;


	// Tests -------------------------------

	@Test
	public void testCreate() {
		EducationRecord educationRecord;
		Curriculum curriculum;

		this.authenticate("ranger1");

		curriculum = this.curriculumService.findByActualRanger();

		educationRecord = this.educationRecordService.create(curriculum);

		Assert.isNull(educationRecord.getTitleOfDiploma());
		Assert.isNull(educationRecord.getStartingDate());
		Assert.isNull(educationRecord.getEndingDate());
		Assert.isNull(educationRecord.getInstitution());
		Assert.isNull(educationRecord.getAttachment());

		Assert.notNull(educationRecord.getComments());
		Assert.isTrue(educationRecord.getComments().isEmpty());

		Assert.notNull(educationRecord.getCurriculum());
		Assert.isTrue(educationRecord.getCurriculum().equals(curriculum));

		this.unauthenticate();
	}

	@Test
	public void testSave() {
		Ranger ranger;
		EducationRecord educationRecord = null;
		final long millis = System.currentTimeMillis() - 10000;

		this.authenticate("ranger1");

		ranger = this.rangerService.findByUserAccount(LoginService.getPrincipal());

		for (final EducationRecord er : ranger.getCurriculum().getEducationRecords()) {
			educationRecord = er;
			break;
		}

		final String titleOfDiploma = "Title of diploma";
		final Date startingDate = new Date(millis);
		final Date endingDate = new Date();
		final String institution = "Institution";
		final String attachment = "http://www.attachment.com";
		final List<String> comments = new ArrayList<String>();
		comments.add("comment1");

		educationRecord.setAttachment(attachment);
		educationRecord.setComments(comments);
		educationRecord.setEndingDate(endingDate);
		educationRecord.setInstitution(institution);
		educationRecord.setStartingDate(startingDate);
		educationRecord.setTitleOfDiploma(titleOfDiploma);

		final EducationRecord educationRecordS = this.educationRecordService.save(educationRecord);

		Assert.isTrue(educationRecordS.getAttachment().equals(attachment));
		Assert.isTrue(educationRecordS.getComments().containsAll(comments));
		Assert.isTrue(educationRecordS.getEndingDate().equals(endingDate));
		Assert.isTrue(educationRecordS.getStartingDate().equals(startingDate));
		Assert.isTrue(educationRecordS.getTitleOfDiploma().equals(titleOfDiploma));

		this.unauthenticate();
	}

	@Test
	public void testFindByCurriculum() {
		Curriculum curriculum = null;
		Collection<Curriculum> curriculums;
		final Collection<EducationRecord> educationRecords;

		this.authenticate("ranger1");

		curriculums = this.curriculumRepository.findAll();

		for (final Curriculum c : curriculums) {
			curriculum = c;
			break;
		}

		educationRecords = this.educationRecordService.findByCurriculum(curriculum);

		Assert.isTrue(educationRecords.containsAll(curriculum.getEducationRecords()));

		this.unauthenticate();
	}

	@Test
	public void testDelete() {
		Ranger ranger;
		EducationRecord educationRecord = null;

		this.authenticate("ranger1");

		ranger = this.rangerService.findByUserAccount(LoginService.getPrincipal());

		for (final EducationRecord er : ranger.getCurriculum().getEducationRecords()) {
			educationRecord = er;
			break;
		}

		this.educationRecordService.delete(educationRecord);

		Assert.isTrue(this.educationRecordRepository.findOne(educationRecord.getId()) == null);

		this.unauthenticate();
	}
}
