
package services;

import java.util.ArrayList;
import java.util.Collection;
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
import domain.MiscellaneousRecord;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	//Service under test
	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

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

		final MiscellaneousRecord testRecord = this.miscellaneousRecordService.create(this.curriculum);

		Assert.notNull(testRecord);
		Assert.isNull(testRecord.getTitle());
		Assert.isNull(testRecord.getAttachment());
		Assert.isNull(testRecord.getComments());
		Assert.notNull(testRecord.getCurriculum());
		Assert.isTrue(testRecord.getCurriculum().equals(this.curriculum));

		this.unauthenticate();

	}

	@Test
	public void testFindByCurriculum() {

		this.authenticate("ranger1");

		final Curriculum rangerCurriculum = this.curriculumService.findByActualRanger();
		final Collection<MiscellaneousRecord> retrievedRecords = this.miscellaneousRecordService.findByCurriculum(this.curriculum);

		Assert.isTrue(rangerCurriculum.getMiscellaneousRecords().containsAll(retrievedRecords));
		Assert.isTrue(rangerCurriculum.getMiscellaneousRecords().size() == retrievedRecords.size());

		this.unauthenticate();

	}

	@Test
	public void testFindOne() {

		this.authenticate("ranger1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		final List<MiscellaneousRecord> miscellaneousRecords = new ArrayList<MiscellaneousRecord>(ranger.getCurriculum().getMiscellaneousRecords());
		final MiscellaneousRecord miscellaneousRecord = miscellaneousRecords.get(0);
		final MiscellaneousRecord foundMiscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecord.getId());

		Assert.notNull(foundMiscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.equals(foundMiscellaneousRecord));

		this.unauthenticate();

	}

	@Test
	public void testFindAll() {

		this.authenticate("ranger1");

		final Collection<MiscellaneousRecord> miscellaneousRecords = new HashSet<MiscellaneousRecord>();

		for (final Curriculum c : this.curriculumService.findAll())
			miscellaneousRecords.addAll(c.getMiscellaneousRecords());

		final Collection<MiscellaneousRecord> foundMiscellaneousRecords = this.miscellaneousRecordService.findAll();

		Assert.notNull(foundMiscellaneousRecords);
		Assert.isTrue(miscellaneousRecords.containsAll(foundMiscellaneousRecords));
		Assert.isTrue(foundMiscellaneousRecords.containsAll(miscellaneousRecords));
		Assert.isTrue(miscellaneousRecords.size() == foundMiscellaneousRecords.size());

		this.unauthenticate();

	}

	@Test
	public void testSave() {

		this.authenticate("ranger1");

		final MiscellaneousRecord testRecord = this.miscellaneousRecordService.create(this.curriculum);

		testRecord.setTitle("Title on Electromagnetism Applied to Microcomputers");
		testRecord.setAttachment("http://www.google.es/mititulo.jpg");
		testRecord.setComments("So hard to get this. Very interesting");

		final MiscellaneousRecord savedRecord = this.miscellaneousRecordService.save(testRecord);

		Assert.notNull(savedRecord);
		Assert.isTrue(testRecord.getTitle().equals(savedRecord.getTitle()));
		Assert.isTrue(testRecord.getAttachment().equals(savedRecord.getAttachment()));
		Assert.isTrue(testRecord.getComments().equals(savedRecord.getComments()));

		Assert.isTrue(this.miscellaneousRecordService.findByCurriculum(this.curriculum).contains(savedRecord));

		this.unauthenticate();

	}

	@Test
	public void testDelete() {

		this.authenticate("ranger1");

		final MiscellaneousRecord testRecord = this.miscellaneousRecordService.create(this.curriculum);

		testRecord.setTitle("Title on Electromagnetism Applied to Microcomputers");
		testRecord.setAttachment("http://www.google.es/mititulo.jpg");
		testRecord.setComments("So hard to get this. Very interesting");

		final MiscellaneousRecord savedRecord = this.miscellaneousRecordService.save(testRecord);

		this.miscellaneousRecordService.delete(savedRecord);

		Assert.isTrue(!this.miscellaneousRecordService.findByCurriculum(this.curriculum).contains(savedRecord));

		this.unauthenticate();

	}
}
