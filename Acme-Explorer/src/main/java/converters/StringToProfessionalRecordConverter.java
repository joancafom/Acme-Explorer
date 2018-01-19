
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.ProfessionalRecordRepository;
import domain.ProfessionalRecord;

@Component
@Transactional
public class StringToProfessionalRecordConverter implements Converter<String, ProfessionalRecord> {

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;


	@Override
	public ProfessionalRecord convert(final String text) {
		ProfessionalRecord res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.professionalRecordRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}

}
