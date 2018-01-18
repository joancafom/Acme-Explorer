
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.PersonalRecord;

@Component
@Transactional
public class PersonalRecordToStringConverter implements Converter<PersonalRecord, String> {

	@Override
	public String convert(final PersonalRecord personalRecord) {
		String res;

		if (personalRecord == null)
			res = null;
		else
			res = String.valueOf(personalRecord.getId());

		return res;
	}

}
