
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.TripApplication;

@Component
@Transactional
public class TripApplicationToStringConverter implements Converter<TripApplication, String> {

	@Override
	public String convert(TripApplication tripApplication) {
		String res;
		
		if (tripApplication == null)
			res = null;
		else
			res = String.valueOf(tripApplication.getId());
		
		return res;
	}
}
