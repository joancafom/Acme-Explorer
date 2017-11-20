
package services;

import java.util.Random;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curriculum;
import domain.PersonalRecord;
import domain.Ranger;
import domain.Trip;

@Service
@Transactional
public class CurriculumService {

	//Managed Repository
	@Autowired
	private CurriculumRepository	curriculumRepository;

	//Supporting Services
	@Autowired
	private RangerService			rangerService;


	//CRUD operations

	public Curriculum create(final PersonalRecord personalRecord) {
		String ticker = "";

		Assert.notNull(personalRecord);

		final UserAccount userAccount = LoginService.getPrincipal();

		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		Assert.isTrue(personalRecord.getCurriculum().getRanger().equals(ranger));

		final Curriculum res = new Curriculum();

		res.setRanger(ranger);
		res.setPersonalRecord(personalRecord);

		final LocalDate date = new LocalDate();
		final Integer year = new Integer(date.getYear());
		final String yy = new String(year.toString());

		final Integer month = new Integer(date.getMonthOfYear());
		final String mm = new String(month.toString());

		final Integer day = new Integer(date.getDayOfMonth());
		final String dd = new String(day.toString());

		final String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String wwww = "";

		for (int i = 0; i < 4; i++) {
			final Random r = new Random();
			wwww += abc.charAt(r.nextInt(abc.length()));
		}

		ticker = yy.substring(2).toUpperCase() + mm.toUpperCase() + dd.toUpperCase() + "-" + wwww.toUpperCase();

		res.setTicker(ticker);
		personalRecord.setCurriculum(res);

		return res;
	}

	public Curriculum findOne(final int curriculumId) {

		final UserAccount userAccount = LoginService.getPrincipal();

		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		final Curriculum res = this.curriculumRepository.findOne(curriculumId);

		Assert.isTrue(res.getRanger().equals(ranger));

		return res;
	}

	public Curriculum save(final Curriculum curriculum) {

		final UserAccount userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.equals(curriculum.getRanger().getUserAccount()));

		return this.curriculumRepository.save(curriculum);
	}

	public void delete(final Curriculum curriculum) {

		final UserAccount userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.equals(curriculum.getRanger().getUserAccount()));

		this.curriculumRepository.delete(curriculum);
	}

	//Business operations

	public Curriculum findByTrip(final Trip t) {
		Assert.notNull(t);

		//final UserAccount userAccount = LoginService.getPrincipal();
		//Assert.isNull(userAccount);

		return this.curriculumRepository.findByTripId(t.getId());
	}

	public Curriculum findByActualRanger() {

		final UserAccount userAccount = LoginService.getPrincipal();

		final Ranger ranger = this.rangerService.findByUserAccount(userAccount);

		return this.curriculumRepository.findByRangerId(ranger.getId());
	}

	public Curriculum findByRanger(final Ranger ranger) {

		//final UserAccount userAccount = LoginService.getPrincipal();
		//Assert.isNull(userAccount);

		return this.curriculumRepository.findByRangerId(ranger.getId());
	}
}
