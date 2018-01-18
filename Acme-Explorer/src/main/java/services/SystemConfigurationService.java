
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
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
		sysConfig.setNextTicker(0);

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
				if (!sysConfig.equals(sC))
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

	public String getTickerAndUpdateNext() {

		final SystemConfiguration sysConfig = this.getCurrentSystemConfiguration();
		Assert.notNull(sysConfig);

		final int index = sysConfig.getNextTicker();
		sysConfig.setNextTicker(index + 1);

		//We compute the WWWW part of the ticker

		final String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		final int pos0 = index % 26;
		final int pos1 = index >= 26 ? (index / 26) % 26 : 0;
		final int pos2 = index >= 26 * 26 ? (index / (26 * 26)) % 26 : 0;
		final int pos3 = index >= 26 * 26 * 26 ? (index / (26 * 26 * 26)) % 26 : 0;

		final char[] wwww = {
			abc.charAt(pos3), abc.charAt(pos2), abc.charAt(pos1), abc.charAt(pos0)
		};

		//Now we compute the YYMMDD

		final LocalDate date = new LocalDate();
		final Integer year = new Integer(date.getYear());
		final String yy = new String(year.toString());

		final Integer month = new Integer(date.getMonthOfYear());
		final String mm = new String(month.toString());

		final Integer day = new Integer(date.getDayOfMonth());
		final String dd = new String(day.toString());

		final String ticker = yy.substring(2).toUpperCase() + mm.toUpperCase() + dd.toUpperCase() + "-" + new String(wwww);

		return ticker;
	}
}
