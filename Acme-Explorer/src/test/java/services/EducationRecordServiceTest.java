
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import security.UserAccount;
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
	private EducationRecordService	educationRecordService;

	// Supporting services -----------------

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private RangerService			rangerService;


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

		Assert.isNull(educationRecord.getComments());

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
		final String comments = "comment";

		educationRecord.setAttachment(attachment);
		educationRecord.setComments(comments);
		educationRecord.setEndingDate(endingDate);
		educationRecord.setInstitution(institution);
		educationRecord.setStartingDate(startingDate);
		educationRecord.setTitleOfDiploma(titleOfDiploma);

		final EducationRecord educationRecordS = this.educationRecordService.save(educationRecord);

		Assert.isTrue(educationRecordS.getAttachment().equals(attachment));
		Assert.isTrue(educationRecordS.getComments().equals(comments));
		Assert.isTrue(educationRecordS.getEndingDate().equals(endingDate));
		Assert.isTrue(educationRecordS.getStartingDate().equals(startingDate));
		Assert.isTrue(educationRecordS.getTitleOfDiploma().equals(titleOfDiploma));
		Assert.notNull(this.educationRecordService.findOne(educationRecordS.getId()));
		Assert.isTrue(this.educationRecordService.findOne(educationRecordS.getId()).equals(educationRecordS));

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {

		this.authenticate("ranger1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		final List<EducationRecord> educationRecords = new ArrayList<EducationRecord>(ranger.getCurriculum().getEducationRecords());
		final EducationRecord educationRecord = educationRecords.get(0);
		final EducationRecord foundEucationRecord = this.educationRecordService.findOne(educationRecord.getId());

		Assert.notNull(foundEucationRecord);
		Assert.isTrue(educationRecord.equals(foundEucationRecord));

		this.unauthenticate();

	}

	@Test
	public void testFindAll() {

		this.authenticate("ranger1");

		final Collection<EducationRecord> educationRecords = new HashSet<EducationRecord>();

		for (final Curriculum c : this.curriculumService.findAll())
			educationRecords.addAll(c.getEducationRecords());

		final Collection<EducationRecord> foundEducationRecords = this.educationRecordService.findAll();

		Assert.notNull(foundEducationRecords);
		Assert.isTrue(educationRecords.containsAll(foundEducationRecords));
		Assert.isTrue(foundEducationRecords.containsAll(educationRecords));
		Assert.isTrue(educationRecords.size() == foundEducationRecords.size());

		this.unauthenticate();

	}

	@Test
	public void testFindByCurriculum() {

		Curriculum curriculum = null;
		Collection<Curriculum> curriculums;

		final Collection<EducationRecord> educationRecords;

		this.authenticate("ranger1");

		curriculums = this.curriculumService.findAll();

		for (final Curriculum c : curriculums) {
			curriculum = c;
			break;
		}

		educationRecords = this.educationRecordService.findByCurriculum(curriculum);

		Assert.isTrue(educationRecords.containsAll(curriculum.getEducationRecords()));
		Assert.isTrue(curriculum.getEducationRecords().containsAll(educationRecords));
		Assert.isTrue(curriculum.getEducationRecords().size() == educationRecords.size());

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

		Assert.isNull(this.educationRecordService.findOne(educationRecord.getId()));

		this.unauthenticate();
	}
}
