
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Tag;

@Component
@Transactional
public class TagToStringConverter implements Converter<Tag, String> {

	@Override
	public String convert(final Tag tag) {
		String res;

		if (tag == null)
			res = null;
		else
			res = String.valueOf(tag.getId());

		return res;
	}
}
