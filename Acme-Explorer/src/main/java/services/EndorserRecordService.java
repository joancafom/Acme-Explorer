
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.Curriculum;
import domain.EndorserRecord;

@Service
@Transactional
public class EndorserRecordService {

	/* Repository */
	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;


	//External Repositories

	public EndorserRecord create(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		final EndorserRecord endorserRecord = new EndorserRecord();
		final Collection<String> comments = new ArrayList<String>();

		endorserRecord.setCurriculum(curriculum);
		endorserRecord.setComments(comments);

		curriculum.getEndorserRecords().add(endorserRecord);

		return endorserRecord;
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		return this.endorserRecordRepository.save(endorserRecord);
	}

	public void delete(final EndorserRecord endorserRecord) {
		this.endorserRecordRepository.delete(endorserRecord);
	}

}
