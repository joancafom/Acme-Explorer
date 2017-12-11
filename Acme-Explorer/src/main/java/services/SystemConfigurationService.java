
package services;

import java.util.ArrayList;
import java.util.Collection;

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


	//CRUD Methods ----------------

	public SystemConfiguration create() {

		final SystemConfiguration sysConfig = new SystemConfiguration();

		sysConfig.setSpamWords(new ArrayList<String>());

		return sysConfig;
	}

	public SystemConfiguration findOne(final int systemConfigurationId) {

		return this.systemConfigurationRepository.findOne(systemConfigurationId);
	}

	public Collection<SystemConfiguration> findAll() {

		return this.systemConfigurationRepository.findAll();
	}

	public SystemConfiguration save(final SystemConfiguration sC) {
		final UserAccount userAccount = LoginService.getPrincipal();
		final Admin admin = this.adminService.findByUserAccount(userAccount);

		Assert.notNull(admin);
		Assert.notNull(sC);

		if (this.findAll() != null)
			for (final SystemConfiguration sysConfig : this.findAll())
				this.delete(sysConfig);

		return this.systemConfigurationRepository.save(sC);
	}

	public void delete(final SystemConfiguration systemConfiguration) {
		this.systemConfigurationRepository.delete(systemConfiguration);
	}

	//Other Business Methods

	public SystemConfiguration getCurrentSystemConfiguration() {

		final Collection<SystemConfiguration> allSysConfig = this.findAll();
		SystemConfiguration res;

		if (allSysConfig == null)
			res = null;
		else
			res = allSysConfig.iterator().next();

		return res;

	}
}
