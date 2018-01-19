
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.TagValue;

@Component
@Transactional
public class TagValueToStringConverter implements Converter<TagValue, String> {

	@Override
	public String convert(final TagValue tagValue) {
		String res;

		if (tagValue == null)
			res = null;
		else
			res = String.valueOf(tagValue.getId());

		return res;
	}
}
