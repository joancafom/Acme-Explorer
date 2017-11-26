
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditionRepository;
import security.LoginService;
import security.UserAccount;
import domain.Audition;
import domain.Auditor;
import domain.Trip;

@Service
@Transactional
public class AuditionService {

	/* Repositories */
	@Autowired
	private AuditionRepository	auditionRepository;

	/* Services */
	@Autowired
	private AuditorService		auditorService;


	/* CRUD */
	public Audition create(final Trip t) {
		long millis;
		final Date moment;

		final UserAccount userAccount = LoginService.getPrincipal();

		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);

		Assert.notNull(t);

		for (final Audition a : auditor.getAuditions())
			Assert.isTrue(!a.getTrip().equals(t));

		final Audition audition = new Audition();
		audition.setTrip(t);
		audition.setAuditor(auditor);

		final Collection<String> attachments = new ArrayList<String>();
		audition.setAttachments(attachments);

		millis = System.currentTimeMillis() - 1000;
		moment = new Date(millis);

		audition.setMoment(moment);
		audition.setIsFinal(false);

		t.getAuditions().add(audition);

		return audition;
	}

	public Collection<Audition> findByCurrentAuditor() {

		final UserAccount userAccount = LoginService.getPrincipal();

		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);

		final Collection<Audition> auditions = this.auditionRepository.findAuditionsManagedByAuditor(auditor.getId());

		return auditions;

	}

	public Audition save(final Audition audition) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(!this.auditionRepository.findOne(audition.getId()).getIsFinal());

		Assert.isTrue(audition.getAuditor().getUserAccount().equals(userAccount));

		return this.auditionRepository.save(audition);

	}

	public void delete(final Audition audition) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(!audition.getIsFinal());

		Assert.isTrue(audition.getAuditor().getUserAccount().equals(userAccount));

		this.auditionRepository.delete(audition);
	}

	public Collection<Audition> findAll() {
		return this.auditionRepository.findAll();
	}

	public Audition findOne(final int id) {
		return this.auditionRepository.findOne(id);
	}

	public Collection<Audition> findByTrip(final Trip t) {
		Assert.notNull(t);
		return this.auditionRepository.findByTripId(t.getId());
	}

}
