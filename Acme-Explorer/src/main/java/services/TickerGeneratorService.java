
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TickerGeneratorRepository;
import domain.TickerGenerator;

@Service
@Transactional
public class TickerGeneratorService {

	//Managed Repository
	@Autowired
	private TickerGeneratorRepository	tickerGeneratorRepository;


	public TickerGenerator create(final int quantity) {

		Assert.isTrue(quantity >= 1);

		//With 26 letters, the max combination you can get is 26^4 = 456976
		Assert.isTrue(quantity <= 456976);

		final TickerGenerator tickerGenerator = new TickerGenerator();

		final Collection<String> tickers = this.generateTickers(quantity);

		tickerGenerator.setTickers(tickers);

		return tickerGenerator;

	}

	public TickerGenerator findOne(final int tickerGeneratorId) {

		return this.tickerGeneratorRepository.findOne(tickerGeneratorId);
	}

	public Collection<TickerGenerator> findAll() {
		return this.tickerGeneratorRepository.findAll();
	}

	public TickerGenerator save(final TickerGenerator tickerGenerator) {

		Assert.notNull(tickerGenerator);

		if (this.findAll() != null)
			for (final TickerGenerator tg : this.findAll())
				this.delete(tg);

		return this.tickerGeneratorRepository.save(tickerGenerator);
	}

	public void delete(final TickerGenerator tickerGenerator) {

		this.tickerGeneratorRepository.delete(tickerGenerator);
	}

	//Other Business Methods

	public String getTickerAndRemove() {

		final String res;

		final List<TickerGenerator> tgs = new ArrayList<TickerGenerator>(this.findAll());
		Assert.isTrue(tgs.size() == 1);

		final TickerGenerator tg = tgs.get(0);

		res = tg.getTickers().iterator().next();

		tg.getTickers().remove(res);

		return res;

	}

	//Ticker Generation Auxiliary Method

	private Collection<String> generateTickers(final int quantity) {

		final Collection<String> res = new ArrayList<String>();

		final String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int index = 0;

		int pos3, pos2, pos1, pos0;

		while (index < quantity) {

			pos0 = index % 26;
			pos1 = index >= 26 ? (index / 26) % 26 : 0;
			pos2 = index >= 26 * 26 ? (index / (26 * 26)) % 26 : 0;
			pos3 = index >= 26 * 26 * 26 ? (index / (26 * 26 * 26)) % 26 : 0;

			final char[] ticker = {
				abc.charAt(pos3), abc.charAt(pos2), abc.charAt(pos1), abc.charAt(pos0)
			};

			res.add(new String(ticker));

			index++;

		}

		return res;
	}
}
