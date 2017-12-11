
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

import repositories.SystemConfigurationRepository;
import utilities.AbstractTest;
import domain.SystemConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SystemConfigurationServiceTest extends AbstractTest {

	@Autowired
	SystemConfigurationRepository	systemConfigurationRepository;

	@Autowired
	SystemConfigurationService		systemConfigurationService;


	@Test
	public void testSave() {
		this.authenticate("admin1");

		final List<SystemConfiguration> sCs = new ArrayList<SystemConfiguration>(this.systemConfigurationRepository.findAll());
		final SystemConfiguration sC2 = sCs.get(0);
		sC2.setCacheTime(4);
		sC2.setMaxNumResults(80);
		this.systemConfigurationService.save(sC2);
		final List<SystemConfiguration> sCs2 = new ArrayList<SystemConfiguration>(this.systemConfigurationRepository.findAll());
		final SystemConfiguration sC3 = sCs2.get(0);
		Assert.isTrue(sC3.getMaxNumResults() == 80 && sC3.getCacheTime() == 4);
	}
}
