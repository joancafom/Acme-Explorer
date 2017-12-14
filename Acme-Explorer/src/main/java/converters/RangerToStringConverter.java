
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Ranger;

@Component
@Transactional
public class RangerToStringConverter implements Converter<Ranger, String> {

	@Override
	public String convert(final Ranger ranger) {
		String res;

		if (ranger == null)
			res = null;
		else
			res = String.valueOf(ranger.getId());

		return res;
	}
}
