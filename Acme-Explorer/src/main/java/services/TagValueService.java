
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TagValueRepository;
import domain.Tag;
import domain.TagValue;
import domain.Trip;

@Service
@Transactional
public class TagValueService {

	// Managed repository ------------------

	@Autowired
	private TagValueRepository	tagValueRepository;


	// Supporting services -----------------

	// Constructors ------------------------

	public TagValueService() {
		super();
	}

	// Simple CRUD methods -----------------

	public TagValue create(final Trip trip, final Tag tag) {
		TagValue tagValue;

		Assert.notNull(trip);
		Assert.notNull(tag);

		for (final TagValue tv : trip.getTagValues())
			Assert.isTrue(tv.getTag().getId() != tag.getId());

		tagValue = new TagValue();

		tagValue.setTag(tag);
		tagValue.setTrip(trip);

		return tagValue;
	}

	public void delete(final TagValue tagValue) {

		Assert.notNull(tagValue);
		Assert.isTrue(this.tagValueRepository.exists(tagValue.getId()));

		this.tagValueRepository.delete(tagValue);
	}

	// Other business methods --------------

}
