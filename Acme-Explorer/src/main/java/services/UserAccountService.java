
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;

@Service
@Transactional
public class UserAccountService {

	//Managed Repository
	@Autowired
	private UserAccountRepository	userAccountRepository;


	//CRUD

	public UserAccount create() {

		final UserAccount res = new UserAccount();

		res.setAuthorities(new ArrayList<Authority>());
		res.setIsLocked(false);

		return res;

	}

	public UserAccount findOne(final int userAccountId) {
		return this.userAccountRepository.findOne(userAccountId);
	}

	public Collection<UserAccount> findAll() {
		return this.userAccountRepository.findAll();
	}

	public UserAccount save(final UserAccount userAccount) {
		return this.userAccountRepository.save(userAccount);
	}

	public void delete(final UserAccount userAccount) {
		this.userAccountRepository.delete(userAccount);
	}

}
