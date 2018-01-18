
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curriculum;
import domain.EndorserRecord;
import domain.Ranger;

@Service
@Transactional
public class EndorserRecordService {

	/* Repository */
	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	//Supporting Services
	@Autowired
	private RangerService				rangerService;


	public EndorserRecord create(final Curriculum curriculum) {
		Assert.notNull(curriculum);

		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		Assert.notNull(ranger);
		Assert.isTrue(curriculum.getRanger().equals(ranger));

		final EndorserRecord endorserRecord = new EndorserRecord();

		endorserRecord.setCurriculum(curriculum);

		curriculum.getEndorserRecords().add(endorserRecord);

		return endorserRecord;
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		return this.endorserRecordRepository.save(endorserRecord);
	}

	public Collection<EndorserRecord> findAll() {
		return this.endorserRecordRepository.findAll();
	}

	public EndorserRecord findOne(final int Id) {
		return this.endorserRecordRepository.findOne(Id);
	}

	public void delete(final EndorserRecord endorserRecord) {
		this.endorserRecordRepository.delete(endorserRecord);
	}

	public Collection<EndorserRecord> findByCurriculum(final Curriculum curriculum) {
		final UserAccount userAccount = LoginService.getPrincipal();

		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getRanger().getUserAccount().equals(userAccount));

		return this.endorserRecordRepository.findByCurriculumId(curriculum.getId());
	}

}
