
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TagRepository;
import domain.Tag;
import domain.TagValue;
import domain.Trip;

@Service
@Transactional
public class TagService {

	//Managed Repository
	@Autowired
	private TagRepository	tagRepository;
	@Autowired
	private TagValueService	tagValueService;


	//Simple CRUD operations
	public Tag create() {

		final Tag res = new Tag();

		res.setTagValues(new ArrayList<TagValue>());

		return res;
	}

	public Collection<Tag> findAll() {

		return this.tagRepository.findAll();
	}

	public Tag findOne(final int tagId) {

		return this.tagRepository.findOne(tagId);
	}

	public Tag save(final Tag tag) {

		Assert.notNull(tag);

		//We must check that there is no Trip tagged to this before modifying
		Assert.isTrue(tag.getTagValues().isEmpty());

		return this.tagRepository.save(tag);
	}

	public void delete(final Tag tag) {

		Assert.notNull(tag);

		//Do we need this?
		//If we remove one Tag, its TagValue(s) disappears, if any.
		if (!tag.getTagValues().isEmpty())
			for (final TagValue tv : tag.getTagValues())
				this.tagValueService.delete(tv);

		this.tagRepository.delete(tag);
	}

	//Other Business Methods

	public Collection<Tag> getTagsByTrip(final Trip trip) {

		Assert.notNull(trip);
		final Collection<Tag> res;

		res = this.tagRepository.getTripTags(trip.getId());

		return res;
	}
}
