
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Explorer;
import domain.Finder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private FinderService	finderService;

	// Supporting repositories -------------

	// Supporting services -----------------

	@Autowired
	private ExplorerService	explorerService;


	// Tests -------------------------------

	@Test
	public void testSave() {
		Explorer explorer;
		Finder finder;

		this.authenticate("explorer1");

		explorer = this.explorerService.findByUserAccount(LoginService.getPrincipal());
		finder = explorer.getFinder();

		final int cacheTime = 10;
		final String keyword = "Keyword";
		final double maxRange = 6000d;
		final Date minDate = new Date();
		final double minRange = 200.5;

		finder.setCacheTime(cacheTime);
		finder.setKeyword(keyword);
		finder.setMaxRange(maxRange);
		finder.setMinDate(minDate);
		finder.setMinRange(minRange);

		this.finderService.save(finder);

		Assert.isTrue(explorer.getFinder().getCacheTime().equals(cacheTime));
		Assert.isTrue(explorer.getFinder().getKeyword().equals(keyword));
		Assert.isTrue(explorer.getFinder().getMaxRange().equals(maxRange));
		Assert.isTrue(explorer.getFinder().getMinDate().equals(minDate));
		Assert.isTrue(explorer.getFinder().getMinRange().equals(minRange));

		this.unauthenticate();

	}

}
