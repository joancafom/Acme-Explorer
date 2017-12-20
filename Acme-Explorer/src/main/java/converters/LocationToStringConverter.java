
package converters;

import java.net.URLEncoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Location;

@Component
@Transactional
public class LocationToStringConverter implements Converter<Location, String> {

	@Override
	public String convert(final Location location) {
		String res;
		final StringBuilder builder;

		if (location == null)
			res = null;
		else
			try {
				builder = new StringBuilder();

				builder.append(URLEncoder.encode(Double.toString(location.getCoordinateX()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Double.toString(location.getCoordinateY()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(location.getName(), "UTF-8"));

				res = builder.toString();
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}
		return res;
	}
}
