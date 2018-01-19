
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.SocialIDRepository;
import domain.SocialID;

@Component
@Transactional
public class StringToSocialIDConverter implements Converter<String, SocialID> {

	@Autowired
	private SocialIDRepository	socialIDRepository;


	@Override
	public SocialID convert(final String text) {
		SocialID res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.socialIDRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}
}
