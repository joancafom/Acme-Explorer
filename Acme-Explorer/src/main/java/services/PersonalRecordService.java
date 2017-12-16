
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curriculum;
import domain.PersonalRecord;
import domain.Ranger;

@Service
@Transactional
public class PersonalRecordService {

	/* Repository */
	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	//External Services
	@Autowired
	private RangerService				rangerService;


	/* CRUD */
	public PersonalRecord create(final Curriculum curriculum) {
		Assert.notNull(curriculum);

		final UserAccount userAccount = LoginService.getPrincipal();

		Assert.isTrue(curriculum.getRanger().getUserAccount().equals(userAccount));

		final PersonalRecord personalRecord = new PersonalRecord();

		personalRecord.setCurriculum(curriculum);

		curriculum.setPersonalRecord(personalRecord);

		return personalRecord;

	}

	public PersonalRecord save(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);

		final UserAccount userAccount = LoginService.getPrincipal();

		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		Assert.notNull(ranger);
		Assert.notNull(ranger.getCurriculum());
		Assert.isTrue(personalRecord.getCurriculum().equals(ranger.getCurriculum()));

		return this.personalRecordRepository.save(personalRecord);
	}

	public Collection<PersonalRecord> findAll() {
		return this.personalRecordRepository.findAll();
	}

	public PersonalRecord findOne(final int id) {
		return this.personalRecordRepository.findOne(id);
	}

	public void delete(final PersonalRecord personalRecord) {

		Assert.notNull(personalRecord);

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.equals(personalRecord.getCurriculum().getRanger().getUserAccount()));

		this.personalRecordRepository.delete(personalRecord);
	}

	//Other business methods

	public PersonalRecord findByCurriculum(final Curriculum curriculum) {
		final UserAccount userAccount = LoginService.getPrincipal();

		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getRanger().getUserAccount().equals(userAccount));

		return this.personalRecordRepository.findByCurriculumId(curriculum.getId());
	}
}
