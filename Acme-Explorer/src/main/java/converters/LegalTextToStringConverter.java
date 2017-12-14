
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.LegalText;

@Component
@Transactional
public class LegalTextToStringConverter implements Converter<LegalText, String> {

	@Override
	public String convert(final LegalText legalText) {
		String res;

		if (legalText == null)
			res = null;
		else
			res = String.valueOf(legalText.getId());

		return res;
	}
}
