
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Explorer;

@Component
@Transactional
public class ExplorerToStringConverter implements Converter<Explorer, String> {

	@Override
	public String convert(Explorer explorer) {
		String res;
		
		if (explorer == null)
			res = null;
		else
			res = String.valueOf(explorer.getId());
		
		return res;
	}
}
