
package services;

import java.util.ArrayList;

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
import domain.ProfessionalRecord;
import domain.Ranger;

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


	@Test
	public void testCreate() {

		this.authenticate("ranger3");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		final Curriculum testCurriculum = this.curriculumService.create();

		Assert.notNull(testCurriculum);
		Assert.notNull(testCurriculum.getTicker());
		Assert.notNull(testCurriculum.getEducationRecords());
		Assert.notNull(testCurriculum.getProfessionalRecords());
		Assert.notNull(testCurriculum.getProfessionalRecords());
		Assert.notNull(testCurriculum.getEndorserRecords());
		Assert.notNull(testCurriculum.getMiscellaneousRecords());
		Assert.notNull(testCurriculum.getRanger());
		Assert.isTrue(testCurriculum.getEducationRecords().isEmpty());
		Assert.isTrue(testCurriculum.getProfessionalRecords().isEmpty());
		Assert.isTrue(testCurriculum.getProfessionalRecords().isEmpty());
		Assert.isTrue(testCurriculum.getEndorserRecords().isEmpty());
		Assert.isTrue(testCurriculum.getMiscellaneousRecords().isEmpty());
		Assert.isTrue(testCurriculum.getRanger().equals(ranger));

		this.unauthenticate();

	}
	@Test
	public void testFindOne() {

		this.authenticate("ranger1");
		//Ranger 1 has a curriculum

		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		final Curriculum curriculum = ranger.getCurriculum();
		final Curriculum foundCurriculum = this.curriculumService.findOne(curriculum.getId());

		Assert.notNull(foundCurriculum);
		Assert.isTrue(curriculum.equals(foundCurriculum));

		this.unauthenticate();
	}

	@Test
	public void testSave() {

		this.authenticate("ranger1");
		//Ranger 1 has a curriculum

		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		final Curriculum curriculum = ranger.getCurriculum();

		curriculum.setProfessionalRecords(new ArrayList<ProfessionalRecord>());

		final Curriculum savedCurriculum = this.curriculumService.save(curriculum);

		Assert.notNull(savedCurriculum);
		Assert.isTrue(curriculum.getTicker().equals(savedCurriculum.getTicker()));
		Assert.isTrue(curriculum.getEducationRecords().equals(savedCurriculum.getEducationRecords()));
		Assert.isTrue(curriculum.getProfessionalRecords().equals(savedCurriculum.getProfessionalRecords()));
		Assert.isTrue(curriculum.getEndorserRecords().equals(savedCurriculum.getEndorserRecords()));
		Assert.isTrue(curriculum.getMiscellaneousRecords().equals(savedCurriculum.getMiscellaneousRecords()));
		Assert.notNull(this.curriculumService.findOne(savedCurriculum.getId()));

		this.unauthenticate();
	}

	@Test
	public void testDelete() {

		this.authenticate("ranger1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		final Curriculum curriculum = ranger.getCurriculum();

		this.curriculumService.delete(curriculum);

		Assert.isNull(this.curriculumService.findOne(curriculum.getId()));

		//TODO: ranger save in order to delete it?

		this.unauthenticate();
	}

	@Test
	public void testFindByActualRanger() {

		this.authenticate("ranger1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		final Curriculum actualCurriculum = ranger.getCurriculum();
		final Curriculum foundCurriculum = this.curriculumService.findByActualRanger();

		Assert.notNull(foundCurriculum);
		Assert.isTrue(actualCurriculum.equals(foundCurriculum));

		this.unauthenticate();

	}

	@Test
	public void testFindByRanger() {

		this.authenticate("ranger1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		final Curriculum actualCurriculum = ranger.getCurriculum();

		this.unauthenticate();

		final Curriculum foundCurriculum = this.curriculumService.findByRanger(ranger);

		Assert.notNull(foundCurriculum);
		Assert.isTrue(actualCurriculum.equals(foundCurriculum));
	}

}
