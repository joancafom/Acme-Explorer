
package converters;

import java.net.URLEncoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Contact;

@Component
@Transactional
public class ContactToStringConverter implements Converter<Contact, String> {

	@Override
	public String convert(final Contact contact) {
		String res;
		StringBuilder builder;

		if (contact == null)
			res = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(contact.getEmail(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(contact.getName(), "UTF-8"));
				builder.append(URLEncoder.encode(contact.getPhoneNumber(), "UTF-8"));
				res = builder.toString();
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}

		return res;
	}
}
