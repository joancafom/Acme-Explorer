
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SystemConfigurationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Admin;
import domain.SystemConfiguration;

@Service
@Transactional
public class SystemConfigurationService {

	//Managed Repository ----------

	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;

	//Managed Services ------------

	@Autowired
	private AdminService					adminService;


	//Constructor -----------------

	public SystemConfigurationService() {
		super();
	}

	//CRUD Methods ----------------

	public SystemConfiguration save(final SystemConfiguration sC) {
		final UserAccount userAccount = LoginService.getPrincipal();
		final Admin admin = this.adminService.findByUserAccount(userAccount);

		Assert.notNull(admin);
		Assert.notNull(sC);

		return this.systemConfigurationRepository.save(sC);
	}
}
