
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.SocialID;

@Component
@Transactional
public class SocialIDToStringConverter implements Converter<SocialID, String> {

	@Override
	public String convert(final SocialID socialID) {
		String res;

		if (socialID == null)
			res = null;
		else
			res = String.valueOf(socialID.getId());

		return res;
	}

}
