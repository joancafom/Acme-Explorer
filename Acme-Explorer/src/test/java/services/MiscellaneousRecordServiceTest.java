
package services;

import java.util.Arrays;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.MiscellaneousRecord;

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
		Assert.notNull(testRecord.getComments());
		Assert.notNull(testRecord.getCurriculum());
		Assert.isTrue(testRecord.getCurriculum().equals(this.curriculum));
		Assert.isTrue(testRecord.getComments().isEmpty());

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
	public void testSave() {

		this.authenticate("ranger1");

		final MiscellaneousRecord testRecord = this.miscellaneousRecordService.create(this.curriculum);

		testRecord.setTitle("Title on Electromagnetism Applied to Microcomputers");
		testRecord.setAttachment("http://www.google.es/mititulo.jpg");
		testRecord.setComments(Arrays.asList("So hard to get this", "Very interesting"));

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
		testRecord.setComments(Arrays.asList("So hard to get this", "Very interesting"));

		final MiscellaneousRecord savedRecord = this.miscellaneousRecordService.save(testRecord);

		this.miscellaneousRecordService.delete(savedRecord);

		Assert.isTrue(!this.miscellaneousRecordService.findByCurriculum(this.curriculum).contains(savedRecord));

		this.unauthenticate();

	}
}
