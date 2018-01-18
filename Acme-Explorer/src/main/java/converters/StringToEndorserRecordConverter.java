
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;

@Component
@Transactional
public class StringToEndorserRecordConverter implements Converter<String, EndorserRecord> {

	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;


	@Override
	public EndorserRecord convert(final String text) {
		EndorserRecord res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.endorserRecordRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}

}
