
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;
import domain.Ranger;

@Service
@Transactional
public class CurriculumService {

	//Managed Repository
	@Autowired
	private CurriculumRepository		curriculumRepository;

	//Supporting Services
	@Autowired
	private RangerService				rangerService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	//CRUD operations

	public Curriculum create() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		Assert.notNull(ranger);

		final Curriculum res = new Curriculum();

		final String ticker = this.systemConfigurationService.getTickerAndUpdateNext();

		res.setTicker(ticker);
		res.setRanger(ranger);
		res.setEducationRecords(new ArrayList<EducationRecord>());
		res.setProfessionalRecords(new ArrayList<ProfessionalRecord>());
		res.setEndorserRecords(new ArrayList<EndorserRecord>());
		res.setMiscellaneousRecords(new ArrayList<MiscellaneousRecord>());

		return res;
	}
	public Curriculum findOne(final int curriculumId) {

		final Curriculum res = this.curriculumRepository.findOne(curriculumId);

		return res;
	}

	public Collection<Curriculum> findAll() {

		return this.curriculumRepository.findAll();
	}

	public Curriculum save(final Curriculum curriculum) {

		Assert.notNull(curriculum);

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		Assert.isTrue(userAccount.equals(curriculum.getRanger().getUserAccount()));

		return this.curriculumRepository.save(curriculum);
	}

	public void delete(final Curriculum curriculum) {

		Assert.notNull(curriculum);

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.equals(curriculum.getRanger().getUserAccount()));

		this.curriculumRepository.delete(curriculum);
	}

	//Business operations

	//A ranger can manage HIS own curriculum
	public Curriculum findByActualRanger() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		return this.curriculumRepository.findByRangerId(ranger.getId());
	}

	//An unauthenticated user can display the curriculum of the ranger associated to a Trip
	public Curriculum findByRanger(final Ranger ranger) {

		Assert.notNull(ranger);

		return this.curriculumRepository.findByRangerId(ranger.getId());
	}
}
