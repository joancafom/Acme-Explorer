
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
				if (contact.getEmail() == null)
					builder.append(URLEncoder.encode("", "UTF-8"));
				else
					builder.append(URLEncoder.encode(contact.getEmail().toString(), "UTF-8"));

				builder.append("|");
				builder.append(URLEncoder.encode(contact.getName().toString(), "UTF-8"));
				builder.append("|");
				if (contact.getPhoneNumber() == null)
					builder.append(URLEncoder.encode("", "UTF-8"));
				else
					builder.append(URLEncoder.encode(contact.getPhoneNumber().toString(), "UTF-8"));

				res = builder.toString();
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}

		return res;
	}
}
