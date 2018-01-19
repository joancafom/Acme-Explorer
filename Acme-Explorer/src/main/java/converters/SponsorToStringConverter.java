
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Sponsor;

@Component
@Transactional
public class SponsorToStringConverter implements Converter<Sponsor, String> {

	@Override
	public String convert(final Sponsor sponsor) {
		String res;

		if (sponsor == null)
			res = null;
		else
			res = String.valueOf(sponsor.getId());

		return res;
	}
}
