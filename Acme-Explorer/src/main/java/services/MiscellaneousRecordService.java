
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curriculum;
import domain.MiscellaneousRecord;
import domain.Ranger;

@Service
@Transactional
public class MiscellaneousRecordService {

	//Managed Repository
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	//External Services
	@Autowired
	private RangerService					rangerService;


	public MiscellaneousRecord create(final Curriculum curriculum) {

		Assert.notNull(curriculum);

		final UserAccount userAccount = LoginService.getPrincipal();

		Assert.isTrue(curriculum.getRanger().getUserAccount().equals(userAccount));

		final MiscellaneousRecord miscellaneousRecord = new MiscellaneousRecord();

		miscellaneousRecord.setCurriculum(curriculum);

		curriculum.getMiscellaneousRecords().add(miscellaneousRecord);

		return miscellaneousRecord;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);

		final UserAccount userAccount = LoginService.getPrincipal();

		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		Assert.notNull(ranger);
		Assert.notNull(ranger.getCurriculum());
		Assert.isTrue(miscellaneousRecord.getCurriculum().equals(ranger.getCurriculum()));

		return this.miscellaneousRecordRepository.save(miscellaneousRecord);
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);

		final UserAccount userAccount = LoginService.getPrincipal();

		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		Assert.notNull(ranger);

		final Curriculum rangerCurriculum = ranger.getCurriculum();

		Assert.notNull(rangerCurriculum);
		Assert.isTrue(rangerCurriculum.equals(ranger.getCurriculum()));

		rangerCurriculum.getMiscellaneousRecords().remove(miscellaneousRecord);

		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

	public Collection<MiscellaneousRecord> findAll() {
		return this.miscellaneousRecordRepository.findAll();
	}

	public MiscellaneousRecord findOne(final int id) {
		return this.miscellaneousRecordRepository.findOne(id);
	}

	//Other business methods

	public Collection<MiscellaneousRecord> findByCurriculum(final Curriculum curriculum) {
		final UserAccount userAccount = LoginService.getPrincipal();

		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getRanger().getUserAccount().equals(userAccount));

		return this.miscellaneousRecordRepository.findByCurriculumId(curriculum.getId());
	}

}
