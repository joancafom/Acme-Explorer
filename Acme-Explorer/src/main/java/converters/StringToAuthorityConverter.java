
package converters;

import java.net.URLDecoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import security.Authority;

@Component
@Transactional
public class StringToAuthorityConverter implements Converter<String, Authority> {

	@Override
	public Authority convert(final String text) {

		Authority res;

		if (text == null)
			res = null;
		else
			try {
				res = new Authority();
				res.setAuthority(URLDecoder.decode(text, "UTF-8"));
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}

		return res;
	}
}
