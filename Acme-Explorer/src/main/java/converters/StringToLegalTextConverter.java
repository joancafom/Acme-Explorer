
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.LegalTextRepository;
import domain.LegalText;

@Component
@Transactional
public class StringToLegalTextConverter implements Converter<String, LegalText> {

	@Autowired
	private LegalTextRepository	legalTextRepository;


	@Override
	public LegalText convert(final String text) {
		LegalText res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.legalTextRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}
}
