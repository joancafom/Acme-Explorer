
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.SystemConfiguration;

@Component
@Transactional
public class SystemConfigurationToStringConverter implements Converter<SystemConfiguration, String> {

	@Override
	public String convert(final SystemConfiguration systemConfiguration) {
		String res;

		if (systemConfiguration == null)
			res = null;
		else
			res = String.valueOf(systemConfiguration.getId());

		return res;
	}
}
