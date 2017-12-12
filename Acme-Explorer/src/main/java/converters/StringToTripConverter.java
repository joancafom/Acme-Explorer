
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.TripRepository;
import domain.Trip;

@Component
@Transactional
public class StringToTripConverter implements Converter<String, Trip> {

	@Autowired
	private TripRepository	tripRepository;


	@Override
	public Trip convert(String text) {
		Trip res;
		int id;
		
		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = tripRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return res;
	}
}
