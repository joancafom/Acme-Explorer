
package converters;

import java.net.URLDecoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Location;

@Component
@Transactional
public class StringToLocationConverter implements Converter<String, Location> {

	@Override
	public Location convert(final String text) {

		Location res;
		String parts[];

		if (text == null)
			res = null;
		else
			try {
				parts = text.split("\\|");
				res = new Location();
				res.setCoordinateX(Double.valueOf(URLDecoder.decode(parts[0], "UTF-8")));
				res.setCoordinateY(Double.valueOf(URLDecoder.decode(parts[1], "UTF-8")));
				res.setName(URLDecoder.decode(parts[2], "UTF-8"));
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}

		return res;
	}

}
