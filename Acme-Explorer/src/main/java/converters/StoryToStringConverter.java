
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Story;

@Component
@Transactional
public class StoryToStringConverter implements Converter<Story, String> {

	@Override
	public String convert(final Story story) {
		String res;

		if (story == null)
			res = null;
		else
			res = String.valueOf(story.getId());

		return res;
	}
}
