
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.NoteRepository;
import domain.Note;

@Component
@Transactional
public class StringToNoteConverter implements Converter<String, Note> {

	@Autowired
	private NoteRepository	noteRepository;


	@Override
	public Note convert(final String text) {
		Note res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.noteRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}
}
