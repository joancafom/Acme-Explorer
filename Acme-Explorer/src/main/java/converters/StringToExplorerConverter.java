
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.ExplorerRepository;
import domain.Explorer;

@Component
@Transactional
public class StringToExplorerConverter implements Converter<String, Explorer> {

	@Autowired
	private ExplorerRepository	explorerRepository;


	@Override
	public Explorer convert(String text) {
		Explorer res;
		int id;
		
		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = explorerRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return res;
	}
}
