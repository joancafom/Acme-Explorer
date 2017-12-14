
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.Authority;
import security.UserAccount;
import domain.Audit;
import domain.Auditor;
import domain.Note;

@Service
@Transactional
public class AuditorService {

	//Managed Repository
	@Autowired
	private AuditorRepository	auditorRepository;

	//Supporting Services
	@Autowired
	private ActorService		actorService;


	//Simple CRUD Operation

	public Auditor create(final UserAccount userAccount) {

		Assert.notNull(userAccount);

		Assert.isTrue(userAccount.getAuthorities().isEmpty() || userAccount.getAuthorities().contains(Authority.AUDITOR));
		if (userAccount.getAuthorities().isEmpty()) {
			final Authority auth = new Authority();
			auth.setAuthority(Authority.AUDITOR);
			userAccount.getAuthorities().add(auth);
		}

		final Auditor res = (Auditor) this.actorService.create(userAccount, Auditor.class);

		res.setNotes(new ArrayList<Note>());
		res.setAudits(new ArrayList<Audit>());

		return res;
	}

	public Auditor findOne(final int auditorId) {

		return this.auditorRepository.findOne(auditorId);
	}

	public Collection<Auditor> findAll() {

		return this.auditorRepository.findAll();
	}

	public Auditor save(final Auditor auditor) {

		Assert.notNull(auditor);

		return this.auditorRepository.save(auditor);
	}

	public void delete(final Auditor auditor) {

		Assert.notNull(auditor);

		this.auditorRepository.delete(auditor);
	}

	//Other Business process

	public Auditor findByUserAccount(final UserAccount userAccount) {

		Assert.notNull(userAccount);

		final Auditor res = this.auditorRepository.findByUserAccountId(userAccount.getId());

		return res;
	}

}
