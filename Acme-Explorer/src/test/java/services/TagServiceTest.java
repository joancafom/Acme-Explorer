
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Tag;
import domain.TagValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TagServiceTest extends AbstractTest {

	@Autowired
	private TagService		tagService;
	@Autowired
	private TagValueService	tagValueService;


	@Test
	public void testCreate() {

		this.authenticate("admin1");

		final Tag tag = this.tagService.create();

		Assert.notNull(tag);
		Assert.notNull(tag.getTagValues());
		Assert.isTrue(tag.getTagValues().isEmpty());
		Assert.isNull(tag.getName());

		this.unauthenticate();
	}

	@Test
	public void testFindAll() {

		this.authenticate("admin1");

		//Tags with TagValues
		final Collection<Tag> tags = new HashSet<Tag>();

		for (final TagValue tv : this.tagValueService.findAll())
			tags.add(tv.getTag());

		final Collection<Tag> foundTags = this.tagService.findAll();

		Assert.notNull(foundTags);
		Assert.isTrue(foundTags.containsAll(tags));
		Assert.isTrue(tags.size() <= foundTags.size());

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {

		this.authenticate("admin1");

		final List<Tag> allTags = new ArrayList<Tag>(this.tagService.findAll());
		final Tag toFind = allTags.get(0);
		final Tag foundTag = this.tagService.findOne(toFind.getId());

		Assert.notNull(foundTag);
		Assert.isTrue(toFind.equals(foundTag));

		this.unauthenticate();
	}
	@Test
	public void testSave() {

		final List<Tag> allTags = new ArrayList<Tag>(this.tagService.findAll());
		Tag toModify = new Tag();
		for (final Tag t : allTags)
			if (t.getTagValues().isEmpty()) {
				toModify = t;
				break;
			}

		toModify.setName("Hola");
		final Tag savedTag = this.tagService.save(toModify);

		Assert.notNull(savedTag);
		Assert.isTrue(toModify.getName().equals(savedTag.getName()));
		Assert.isTrue(toModify.getTagValues().equals(savedTag.getTagValues()));

		this.unauthenticate();
	}

	@Test
	public void testDelete() {

		final List<Tag> allTags = new ArrayList<Tag>(this.tagService.findAll());
		Tag toDelete = new Tag();

		for (final Tag t : allTags)
			if (!t.getTagValues().isEmpty()) {
				toDelete = t;
				break;
			}

		this.tagService.delete(toDelete);

		Assert.isNull(this.tagService.findOne(toDelete.getId()));

		this.unauthenticate();
	}
}
