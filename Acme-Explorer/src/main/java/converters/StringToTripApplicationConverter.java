
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.TripApplicationRepository;
import domain.TripApplication;

@Component
@Transactional
public class StringToTripApplicationConverter implements Converter<String, TripApplication> {

	@Autowired
	private TripApplicationRepository	tripApplicationRepository;


	@Override
	public TripApplication convert(String text) {
		TripApplication res;
		int id;
		
		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = tripApplicationRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return res;
	}
}
