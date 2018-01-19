
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.SystemConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SystemConfigurationServiceTest extends AbstractTest {

	@Autowired
	SystemConfigurationService	systemConfigurationService;


	@Test
	public void testSave() {

		this.authenticate("admin");

		final List<SystemConfiguration> sCs = new ArrayList<SystemConfiguration>(this.systemConfigurationService.findAll());
		final SystemConfiguration sC2 = sCs.get(0);
		sC2.setCacheTime(4);
		sC2.setMaxNumResults(80);
		this.systemConfigurationService.save(sC2);
		final List<SystemConfiguration> sCs2 = new ArrayList<SystemConfiguration>(this.systemConfigurationService.findAll());
		final SystemConfiguration sC3 = sCs2.get(0);
		Assert.isTrue(sC3.getMaxNumResults() == 80 && sC3.getCacheTime() == 4);

		this.unauthenticate();
	}

	@Test
	public void testGetTickerAndUpdateNext() {

		this.authenticate("admin");

		final SystemConfiguration sysConfig = this.systemConfigurationService.getCurrentSystemConfiguration();

		final int nextTicker1 = sysConfig.getNextTicker();
		final String ticker1 = this.systemConfigurationService.getTickerAndUpdateNext();
		final int nextTicker2 = sysConfig.getNextTicker();
		final String ticker2 = this.systemConfigurationService.getTickerAndUpdateNext();

		Assert.notNull(ticker1);
		Assert.notNull(ticker2);
		Assert.isTrue(ticker1.compareTo(ticker2) == -1);
		Assert.isTrue(nextTicker2 - nextTicker1 == 1);

		this.unauthenticate();
	}
}
