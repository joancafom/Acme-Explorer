
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.StageRepository;
import domain.Stage;

@Component
@Transactional
public class StringToStageConverter implements Converter<String, Stage> {

	@Autowired
	private StageRepository	stageRepository;


	@Override
	public Stage convert(final String text) {
		Stage res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.stageRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}
}
