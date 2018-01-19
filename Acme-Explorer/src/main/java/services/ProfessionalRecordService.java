
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curriculum;
import domain.ProfessionalRecord;
import domain.Ranger;

@Service
@Transactional
public class ProfessionalRecordService {

	//Managed Repository
	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	//External Services
	@Autowired
	private RangerService					rangerService;


	public ProfessionalRecord create(final Curriculum curriculum) {
		Assert.notNull(curriculum);

		final UserAccount userAccount = LoginService.getPrincipal();

		Assert.isTrue(curriculum.getRanger().getUserAccount().equals(userAccount));

		final ProfessionalRecord professionalRecord = new ProfessionalRecord();

		professionalRecord.setCurriculum(curriculum);

		return professionalRecord;
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);

		final UserAccount userAccount = LoginService.getPrincipal();

		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		Assert.notNull(ranger);
		Assert.notNull(ranger.getCurriculum());
		Assert.isTrue(professionalRecord.getCurriculum().equals(ranger.getCurriculum()));

		return this.professionalRecordRepository.save(professionalRecord);
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);

		final UserAccount userAccount = LoginService.getPrincipal();

		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		Assert.notNull(ranger);

		final Curriculum rangerCurriculum = ranger.getCurriculum();

		Assert.notNull(rangerCurriculum);
		Assert.isTrue(rangerCurriculum.equals(ranger.getCurriculum()));

		rangerCurriculum.getMiscellaneousRecords().remove(professionalRecord);

		this.professionalRecordRepository.delete(professionalRecord);
	}

	public Collection<ProfessionalRecord> findAll() {
		return this.professionalRecordRepository.findAll();
	}

	public ProfessionalRecord findOne(final int id) {
		return this.professionalRecordRepository.findOne(id);
	}

	//Other business methods

	public Collection<ProfessionalRecord> findByCurriculum(final Curriculum curriculum) {
		final UserAccount userAccount = LoginService.getPrincipal();

		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getRanger().getUserAccount().equals(userAccount));

		return this.professionalRecordRepository.findByCurriculumId(curriculum.getId());
	}
}
