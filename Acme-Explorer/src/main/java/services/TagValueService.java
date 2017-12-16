
package services;

import java.util.Collection;

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

	@Autowired
	TagService					tagService;


	// Constructors ------------------------

	public TagValueService() {
		super();
	}

	// Simple CRUD methods -----------------

	public TagValue create(final Trip trip) {
		TagValue tagValue;

		Assert.notNull(trip);

		tagValue = new TagValue();

		tagValue.setTrip(trip);

		return tagValue;
	}

	public Collection<TagValue> findAll() {
		Collection<TagValue> tagValues;

		Assert.notNull(this.tagValueRepository);
		tagValues = this.tagValueRepository.findAll();
		Assert.notNull(tagValues);

		return tagValues;
	}

	public TagValue findOne(final int tagValueId) {
		// REVISAR !!!
		// Debe tener algún assert?
		TagValue tagValue;

		tagValue = this.tagValueRepository.findOne(tagValueId);

		return tagValue;
	}

	public TagValue save(final TagValue tagValue) {

		final Trip trip = tagValue.getTrip();
		Assert.notNull(trip);
		final Tag tag = tagValue.getTag();
		Assert.notNull(tag);

		if (tagValue.getId() == 0)
			Assert.isTrue(!this.tagService.getTagsByTrip(trip).contains(tag));

		return this.tagValueRepository.save(tagValue);
	}
	public void delete(final TagValue tagValue) {
		Assert.notNull(tagValue);

		Assert.isTrue(this.tagValueRepository.exists(tagValue.getId()));

		this.tagValueRepository.delete(tagValue);
	}

	// Other business methods --------------

}
