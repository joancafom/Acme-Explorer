
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.TagRepository;
import domain.Tag;

@Component
@Transactional
public class StringToTagConverter implements Converter<String, Tag> {

	@Autowired
	private TagRepository	tagRepository;


	@Override
	public Tag convert(final String text) {
		Tag res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.tagRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}
}
