
package converters;

import java.net.URLDecoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Contact;

@Component
@Transactional
public class StringToContactConverter implements Converter<String, Contact> {

	@Override
	public Contact convert(final String text) {

		Contact res;
		String parts[];

		if (text == null)
			res = null;
		else
			try {
				parts = text.split("\\|");
				res = new Contact();
				if (URLDecoder.decode(parts[0], "UTF-8").equals(""))
					res.setEmail(null);
				else
					res.setEmail(URLDecoder.decode(parts[0], "UTF-8"));
				if (URLDecoder.decode(parts[1], "UTF-8").equals(""))
					res.setPhoneNumber(null);
				else
					res.setPhoneNumber(URLDecoder.decode(parts[1], "UTF-8"));

				res.setName(URLDecoder.decode(parts[2], "UTF-8"));

			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}

		return res;
	}
}
