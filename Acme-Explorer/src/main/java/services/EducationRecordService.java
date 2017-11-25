
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curriculum;
import domain.EducationRecord;
import domain.Ranger;

@Service
@Transactional
public class EducationRecordService {

	//Managed Repository
	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	//Supporting Services
	@Autowired
	private RangerService				rangerService;


	//Simple CRUD methods
	public EducationRecord create(final Curriculum curriculum) {

		Assert.notNull(curriculum);

		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		Assert.notNull(ranger);
		Assert.isTrue(curriculum.getRanger().equals(ranger));

		final EducationRecord educationRecord = new EducationRecord();

		educationRecord.setCurriculum(curriculum);
		educationRecord.setComments(new ArrayList<String>());

		curriculum.getEducationRecords().add(educationRecord);

		return educationRecord;
	}

	public EducationRecord findOne(final int educationRecordId) {

		return this.educationRecordRepository.findOne(educationRecordId);
	}

	public Collection<EducationRecord> findAll() {

		return this.educationRecordRepository.findAll();
	}

	public EducationRecord save(final EducationRecord educationRecord) {

		Assert.notNull(educationRecord);

		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		Assert.notNull(ranger);
		Assert.notNull(ranger.getCurriculum());
		Assert.isTrue(educationRecord.getCurriculum().equals(ranger.getCurriculum()));

		return this.educationRecordRepository.save(educationRecord);
	}

	public void delete(final EducationRecord educationRecord) {

		Assert.notNull(educationRecord);

		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);
		Assert.notNull(ranger);

		final Curriculum rangerCurriculum = ranger.getCurriculum();

		Assert.notNull(rangerCurriculum);
		Assert.isTrue(rangerCurriculum.equals(educationRecord.getCurriculum()));

		this.educationRecordRepository.delete(educationRecord);
	}

	//Other Business methods

	public Collection<EducationRecord> findByCurriculum(final Curriculum curriculum) {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getRanger().equals(ranger));

		return this.educationRecordRepository.findByCurriculumId(curriculum.getId());
	}

}
