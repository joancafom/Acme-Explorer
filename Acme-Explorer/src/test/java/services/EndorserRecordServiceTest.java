
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
import domain.EndorserRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	//Service under test
	@Autowired
	private EndorserRecordService	endorserRecordService;

	//Other required Services
	@Autowired
	private CurriculumService		curriculumService;

	//Working Variables
	private Curriculum				curriculum;


	@Before
	public void setUpWorkingVariables() {

		this.authenticate("ranger1");

		this.curriculum = this.curriculumService.findByActualRanger();

		this.unauthenticate();
	}

	@Test
	public void testCreate() {

		this.authenticate("ranger1");

		final EndorserRecord testRecord = this.endorserRecordService.create(this.curriculum);

		Assert.notNull(testRecord);
		Assert.isNull(testRecord.getFullName());
		Assert.isNull(testRecord.getEmail());
		Assert.isNull(testRecord.getPhoneNumber());
		Assert.isNull(testRecord.getLinkedInProfile());
		Assert.notNull(testRecord.getComments());
		Assert.isTrue(testRecord.getComments().isEmpty());
		Assert.notNull(testRecord.getCurriculum());
		Assert.isTrue(testRecord.getCurriculum().equals(this.curriculum));
		Assert.isTrue(testRecord.getCurriculum().getEndorserRecords().contains(testRecord));

		this.unauthenticate();

	}

	@Test
	public void testSave() {

		this.authenticate("ranger1");

		final EndorserRecord testRecord = this.endorserRecordService.create(this.curriculum);

		testRecord.setFullName("Endorser Record 1 - Testing");
		testRecord.setEmail("prueba@hotmail.com");
		testRecord.setPhoneNumber("695849584");
		testRecord.setLinkedInProfile("http://www.linkedin.com/endorser1");
		testRecord.getComments().addAll(Arrays.asList("Got this on third grade", "Ich kann spreche Deutsch"));

		final EndorserRecord savedRecord = this.endorserRecordService.save(testRecord);

		Assert.notNull(savedRecord);
		Assert.isTrue(testRecord.getFullName().equals(savedRecord.getFullName()));
		Assert.isTrue(testRecord.getEmail().equals(savedRecord.getEmail()));
		Assert.isTrue(testRecord.getPhoneNumber().equals(savedRecord.getPhoneNumber()));
		Assert.isTrue(testRecord.getLinkedInProfile().equals(savedRecord.getLinkedInProfile()));
		Assert.isTrue(testRecord.getComments().equals(savedRecord.getComments()));

		this.unauthenticate();

	}
	@Test
	public void testDelete() {

		this.authenticate("ranger1");

		final EndorserRecord testRecord = this.endorserRecordService.create(this.curriculum);

		testRecord.setFullName("Endorser Record 1 - Testing");
		testRecord.setEmail("prueba@hotmail.com");
		testRecord.setPhoneNumber("695849584");
		testRecord.setLinkedInProfile("http://www.linkedin.com/endorser1");
		testRecord.setComments(Arrays.asList("Got this on third grade", "Ich kann spreche Deutsch"));

		final EndorserRecord savedRecord = this.endorserRecordService.save(testRecord);

		this.endorserRecordService.delete(savedRecord);

		Assert.isTrue(!this.curriculumService.findByActualRanger().getEndorserRecords().contains(savedRecord));

		this.unauthenticate();

	}

	@Test
	public void testFindByCurriculum() {

		this.authenticate("ranger1");

		final Curriculum rangerCurriculum = this.curriculumService.findByActualRanger();
		final Collection<EndorserRecord> retrievedRecords = this.endorserRecordService.findByCurriculum(this.curriculum);

		Assert.isTrue(rangerCurriculum.getEndorserRecords().containsAll(retrievedRecords));
		Assert.isTrue(rangerCurriculum.getEndorserRecords().size() == retrievedRecords.size());

		this.unauthenticate();

	}
}
