
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.TagValueRepository;
import domain.TagValue;

@Component
@Transactional
public class StringToTagValueConverter implements Converter<String, TagValue> {

	@Autowired
	private TagValueRepository	tagValueRepository;


	@Override
	public TagValue convert(final String text) {
		TagValue res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.tagValueRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}
}
