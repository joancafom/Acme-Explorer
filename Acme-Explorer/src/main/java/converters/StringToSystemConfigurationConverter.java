
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.SystemConfigurationRepository;
import domain.SystemConfiguration;

@Component
@Transactional
public class StringToSystemConfigurationConverter implements Converter<String, SystemConfiguration> {

	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;


	@Override
	public SystemConfiguration convert(final String text) {
		SystemConfiguration res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.systemConfigurationRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}
}
