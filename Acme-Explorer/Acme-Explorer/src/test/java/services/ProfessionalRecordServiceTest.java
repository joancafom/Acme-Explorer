
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
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
import domain.ProfessionalRecord;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	//Service under test
	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	//Other required Services
	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private RangerService				rangerService;

	//Working Variables
	private Curriculum					curriculum;


	@Before
	public void setUpWorkingVariables() {

		this.authenticate("ranger1");

		this.curriculum = this.curriculumService.findByActualRanger();

		this.unauthenticate();
	}

	@Test
	public void testCreate() {

		this.authenticate("ranger1");

		final ProfessionalRecord testRecord = this.professionalRecordService.create(this.curriculum);

		Assert.notNull(testRecord);
		Assert.isNull(testRecord.getCompanyName());
		Assert.isNull(testRecord.getStartingDate());
		Assert.isNull(testRecord.getEndingDate());
		Assert.isNull(testRecord.getRole());
		Assert.isNull(testRecord.getAttachment());
		Assert.isNull(testRecord.getComments());
		Assert.notNull(testRecord.getCurriculum());
		Assert.isTrue(testRecord.getCurriculum().equals(this.curriculum));

		this.unauthenticate();

	}

	@Test
	public void testFindByCurriculum() {

		this.authenticate("ranger1");

		final Curriculum rangerCurriculum = this.curriculum;
		final Collection<ProfessionalRecord> retrievedRecords = this.professionalRecordService.findByCurriculum(this.curriculum);

		Assert.isTrue(rangerCurriculum.getProfessionalRecords().containsAll(retrievedRecords));
		Assert.isTrue(rangerCurriculum.getProfessionalRecords().size() == retrievedRecords.size());

		this.unauthenticate();

	}

	@Test
	public void testSave() {

		this.authenticate("ranger1");

		final ProfessionalRecord testRecord = this.professionalRecordService.create(this.curriculum);

		testRecord.setCompanyName("Apple");
		final long nowMillis = System.currentTimeMillis() - 5000;
		final Date fechaInicio = new Date(nowMillis);
		testRecord.setStartingDate(fechaInicio);
		testRecord.setEndingDate(new Date());
		testRecord.setRole("CEO");
		testRecord.setAttachment("http://www.apple.com/ceo.pdf");
		testRecord.setComments("Best Job Ever");

		final ProfessionalRecord savedRecord = this.professionalRecordService.save(testRecord);

		Assert.notNull(savedRecord);
		Assert.isTrue(testRecord.getCompanyName().equals(savedRecord.getCompanyName()));
		Assert.isTrue(testRecord.getStartingDate().equals(savedRecord.getStartingDate()));
		Assert.isTrue(testRecord.getEndingDate().equals(savedRecord.getEndingDate()));
		Assert.isTrue(testRecord.getRole().equals(savedRecord.getRole()));
		Assert.isTrue(testRecord.getAttachment().equals(savedRecord.getAttachment()));
		Assert.isTrue(testRecord.getComments().equals(savedRecord.getComments()));

		Assert.isTrue(this.professionalRecordService.findByCurriculum(this.curriculum).contains(savedRecord));

		this.unauthenticate();

	}

	@Test
	public void testDelete() {

		this.authenticate("ranger1");

		final ProfessionalRecord testRecord = this.professionalRecordService.create(this.curriculum);

		testRecord.setCompanyName("Apple");
		final long nowMillis = System.currentTimeMillis() - 5000;
		final Date fechaInicio = new Date(nowMillis);
		testRecord.setStartingDate(fechaInicio);
		testRecord.setEndingDate(new Date());
		testRecord.setRole("CEO");
		testRecord.setAttachment("http://www.apple.com/ceo.pdf");
		testRecord.setComments("Best Job Ever");

		final ProfessionalRecord savedRecord = this.professionalRecordService.save(testRecord);

		final Collection<ProfessionalRecord> resultsBefore = this.professionalRecordService.findByCurriculum(this.curriculum);

		this.professionalRecordService.delete(savedRecord);

		final Collection<ProfessionalRecord> resultsAfter = this.professionalRecordService.findByCurriculum(this.curriculum);

		Assert.isTrue(!resultsBefore.equals(resultsAfter));

		this.unauthenticate();

	}

	@Test
	public void testFindOne() {

		this.authenticate("ranger1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		final List<ProfessionalRecord> professionalRecords = new ArrayList<ProfessionalRecord>(ranger.getCurriculum().getProfessionalRecords());
		final ProfessionalRecord professionalRecord = professionalRecords.get(0);
		final ProfessionalRecord foundProfessionalRecord = this.professionalRecordService.findOne(professionalRecord.getId());

		Assert.notNull(foundProfessionalRecord);
		Assert.isTrue(professionalRecord.equals(foundProfessionalRecord));

		this.unauthenticate();

	}

	@Test
	public void testFindAll() {

		this.authenticate("ranger1");

		final Collection<ProfessionalRecord> professionalRecords = new HashSet<ProfessionalRecord>();

		for (final Curriculum c : this.curriculumService.findAll())
			professionalRecords.addAll(c.getProfessionalRecords());

		final Collection<ProfessionalRecord> foundProfessionalRecords = this.professionalRecordService.findAll();

		Assert.notNull(foundProfessionalRecords);
		Assert.isTrue(professionalRecords.containsAll(foundProfessionalRecords));
		Assert.isTrue(foundProfessionalRecords.containsAll(professionalRecords));
		Assert.isTrue(professionalRecords.size() == foundProfessionalRecords.size());

		this.unauthenticate();

	}

}
