
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.SurvivalClassRepository;
import domain.SurvivalClass;

@Component
@Transactional
public class StringToSurvivalClassConverter implements Converter<String, SurvivalClass> {

	@Autowired
	private SurvivalClassRepository	survivalClassRepository;


	@Override
	public SurvivalClass convert(final String text) {
		SurvivalClass res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.survivalClassRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}
}
