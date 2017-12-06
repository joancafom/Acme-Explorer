
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.TickerGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TickerGeneratorServiceTest extends AbstractTest {

	@Autowired
	private TickerGeneratorService	tickerGeneratorService;


	@Test
	public void testCreate() {

		final TickerGenerator tg = this.tickerGeneratorService.create(200);

		Assert.notNull(tg);
		Assert.notNull(tg.getTickers());
		Assert.isTrue(tg.getTickers().size() == 200);

	}

	@Test
	public void testSave() {

		final TickerGenerator tg = this.tickerGeneratorService.create(200);

		final TickerGenerator savedTG = this.tickerGeneratorService.save(tg);

		Assert.notNull(savedTG);
		Assert.notNull(savedTG.getTickers());
		Assert.isTrue(tg.getTickers().equals(savedTG.getTickers()));

		final TickerGenerator tg2 = this.tickerGeneratorService.create(100);
		final TickerGenerator savedTG2 = this.tickerGeneratorService.save(tg2);

		Assert.notNull(savedTG2);
		Assert.notNull(savedTG2.getTickers());
		Assert.isTrue(tg2.getTickers().equals(savedTG2.getTickers()));

		Assert.isTrue(this.tickerGeneratorService.findAll().size() == 1);
	}

	@Test
	public void testFindAll() {

		Collection<TickerGenerator> tgs = this.tickerGeneratorService.findAll();

		Assert.notNull(tgs);
		Assert.isTrue(tgs.size() == 0);

		final TickerGenerator tg = this.tickerGeneratorService.create(200);
		final TickerGenerator savedTG = this.tickerGeneratorService.save(tg);
		final TickerGenerator tg2 = this.tickerGeneratorService.create(100);
		final TickerGenerator savedTG2 = this.tickerGeneratorService.save(tg2);

		tgs = this.tickerGeneratorService.findAll();

		Assert.notNull(tgs);
		Assert.isTrue(tgs.size() == 1);
		Assert.isTrue(tgs.contains(savedTG2));
		Assert.isTrue(!tgs.contains(savedTG));

	}

	@Test
	public void testFindOne() {

		final TickerGenerator tg = this.tickerGeneratorService.create(200);
		final TickerGenerator savedTG = this.tickerGeneratorService.save(tg);

		final TickerGenerator foundTG = this.tickerGeneratorService.findOne(savedTG.getId());

		Assert.notNull(foundTG);
		Assert.isTrue(savedTG.equals(foundTG));
	}

	@Test
	public void testDelete() {

		final TickerGenerator tg = this.tickerGeneratorService.create(200);
		final TickerGenerator savedTG = this.tickerGeneratorService.save(tg);

		this.tickerGeneratorService.delete(savedTG);

		Assert.isTrue(this.tickerGeneratorService.findAll().isEmpty());
	}

	@Test
	public void testGetTickerAndRemove() {

		final TickerGenerator tg = this.tickerGeneratorService.create(200);
		final TickerGenerator savedTG = this.tickerGeneratorService.save(tg);

		final int previousNTickers = savedTG.getTickers().size();
		final String ticker = this.tickerGeneratorService.getTickerAndRemove();
		final int afterNTickers = savedTG.getTickers().size();

		Assert.notNull(ticker);
		Assert.isTrue(!savedTG.getTickers().contains(ticker));
		Assert.isTrue(previousNTickers == (afterNTickers + 1));

	}
}
