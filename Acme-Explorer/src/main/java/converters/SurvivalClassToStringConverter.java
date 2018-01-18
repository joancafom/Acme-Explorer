
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.SurvivalClass;

@Component
@Transactional
public class SurvivalClassToStringConverter implements Converter<SurvivalClass, String> {

	@Override
	public String convert(final SurvivalClass survivalClass) {
		String res;

		if (survivalClass == null)
			res = null;
		else
			res = String.valueOf(survivalClass.getId());

		return res;
	}
}
