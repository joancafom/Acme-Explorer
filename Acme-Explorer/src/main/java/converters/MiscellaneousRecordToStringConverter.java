
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.MiscellaneousRecord;

@Component
@Transactional
public class MiscellaneousRecordToStringConverter implements Converter<MiscellaneousRecord, String> {

	@Override
	public String convert(final MiscellaneousRecord miscellaneousRecord) {
		String res;

		if (miscellaneousRecord == null)
			res = null;
		else
			res = String.valueOf(miscellaneousRecord.getId());

		return res;
	}

}
