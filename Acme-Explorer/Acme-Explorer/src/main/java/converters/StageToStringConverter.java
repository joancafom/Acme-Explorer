
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Stage;

@Component
@Transactional
public class StageToStringConverter implements Converter<Stage, String> {

	@Override
	public String convert(final Stage stage) {
		String res;

		if (stage == null)
			res = null;
		else
			res = String.valueOf(stage.getId());

		return res;
	}
}
