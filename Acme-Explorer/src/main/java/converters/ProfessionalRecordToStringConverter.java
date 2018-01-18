
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.ProfessionalRecord;

@Component
@Transactional
public class ProfessionalRecordToStringConverter implements Converter<ProfessionalRecord, String> {

	@Override
	public String convert(final ProfessionalRecord professionalRecord) {
		String res;

		if (professionalRecord == null)
			res = null;
		else
			res = String.valueOf(professionalRecord.getId());

		return res;
	}

}
