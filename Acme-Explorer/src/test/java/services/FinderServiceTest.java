
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
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
	public void testCreate() {
		Finder finder;

		this.authenticate("explorer1");

		finder = this.finderService.create();

		Assert.isNull(finder.getKeyword());
		Assert.isNull(finder.getMinRange());
		Assert.isNull(finder.getMaxRange());
		Assert.isNull(finder.getMinDate());
		Assert.isNull(finder.getMaxDate());

		this.unauthenticate();
	}

	@Test
	public void testFindAll() {
		// REVISAR !!!
		// Cómo se comprueba que el findAll() funciona correctamente?

		final Integer currentNumberOfFindersInTheXML = 2;

		this.authenticate("admin1");

		final Collection<Finder> finders = this.finderService.findAll();

		Assert.notNull(finders);
		Assert.isTrue(finders.size() == currentNumberOfFindersInTheXML);

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {
		Finder finder1 = null;
		Finder finder2 = null;

		this.authenticate("admin1");

		final Collection<Finder> finders = this.finderService.findAll();

		for (final Finder f : finders)
			if (f != null) {
				finder1 = f;
				break;
			}

		finder2 = this.finderService.findOne(finder1.getId());

		Assert.isTrue(finder1.equals(finder2));

		this.unauthenticate();
	}

	@Test
	public void testSave() {
		Finder finder1 = null;
		Finder finder2 = null;

		this.authenticate("explorer2");

		final Collection<Finder> finders = this.finderService.findAll();

		for (final Finder f : finders)
			if (this.explorerService.findByUserAccount(LoginService.getPrincipal()).getFinder() == f) {
				finder1 = f;
				break;
			}

		finder1.setKeyword("KeyWord");
		finder1.setMinRange(40.5);
		finder1.setMaxRange(10000.4);
		// REVISAR !!!
		// Cómo hacer para las fechas con métodos deprecados?

		finder2 = this.finderService.save(finder1);

		Assert.notNull(finder2);
		Assert.isTrue(finder1.getKeyword().equals(finder2.getKeyword()));
		Assert.isTrue(finder1.getMinRange().equals(finder2.getMinRange()));
		Assert.isTrue(finder1.getMaxRange().equals(finder2.getMaxRange()));

		this.unauthenticate();
	}

	@Test
	public void testFindByPrincipal() {
		Finder finder1 = null;
		Finder finder2 = null;

		this.authenticate("explorer1");

		final Collection<Finder> finders = this.finderService.findAll();

		for (final Finder f : finders)
			if (this.explorerService.findByUserAccount(LoginService.getPrincipal()).getFinder() == f) {
				finder1 = f;
				break;
			}

		finder2 = this.finderService.findByPrincipal();

		Assert.isTrue(finder1.equals(finder2));

		this.unauthenticate();
	}

}
