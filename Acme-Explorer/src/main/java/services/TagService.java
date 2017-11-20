
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TagRepository;
import domain.Tag;
import domain.TagValue;

@Service
@Transactional
public class TagService {

	//Managed Repository
	@Autowired
	private TagRepository	tagRepository;

	//Supporting Services
	@Autowired
	private TagValueService	tagValueService;


	//Constructor
	public TagService() {
		super();
	}

	//Simple CRUD operations
	public Tag create() {

		final Tag res = new Tag();
		final Set<TagValue> tagValues = new HashSet<TagValue>();

		res.setTagValues(tagValues);

		return res;
	}

	public Collection<Tag> findAll() {

		return this.tagRepository.findAll();
	}

	public Tag findOne(final int tagId) {

		Assert.notNull(tagId);

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

		//If we remove one Tag, its TagValue(s) disappears, if any.
		if (!tag.getTagValues().isEmpty())
			for (final TagValue tv : tag.getTagValues())
				this.tagValueService.delete(tv);

		this.tagRepository.delete(tag);
	}
}
