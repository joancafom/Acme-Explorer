
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.UserAccount;
import domain.Auditor;

@Service
@Transactional
public class AuditorService {

	//Managed Repository
	@Autowired
	private AuditorRepository	auditorRepository;


	//Supporting Services

	//Simple CRUD Operation

	//Other Business process

	public Auditor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		final Auditor res = this.auditorRepository.findByUserAccountId(userAccount.getId());

		Assert.notNull(res);

		return res;
	}
}
