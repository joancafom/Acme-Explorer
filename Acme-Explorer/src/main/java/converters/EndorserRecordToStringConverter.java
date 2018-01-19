
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.EndorserRecord;

@Component
@Transactional
public class EndorserRecordToStringConverter implements Converter<EndorserRecord, String> {

	@Override
	public String convert(final EndorserRecord endorserRecord) {
		String res;

		if (endorserRecord == null)
			res = null;
		else
			res = String.valueOf(endorserRecord.getId());

		return res;
	}

}
