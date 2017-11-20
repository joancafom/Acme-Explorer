
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.TagRepository;
import utilities.AbstractTest;
import domain.Tag;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TagServiceTest extends AbstractTest {

	@Autowired
	private TagService		tagService;
	@Autowired
	private TagRepository	tagRepository;


	@Test
	public void testCreate() {
		final Tag tag = this.tagService.create();
		Assert.notNull(tag);
		Assert.isTrue(tag.getTagValues().isEmpty());
		this.unauthenticate();
	}

	@Test
	public void testFindAll() {
		final Collection<Tag> tags = this.tagService.findAll();
		final List<Tag> tags2 = this.tagRepository.findAll();

		Assert.notNull(tags);
		Assert.isTrue(tags.containsAll(tags2) && tags.size() == tags2.size());
		this.unauthenticate();
	}

	@Test
	public void testFindOne() {
		final List<Tag> tags2 = this.tagRepository.findAll();
		final Tag toFind = tags2.get(0);

		Assert.isTrue(toFind.equals(this.tagService.findOne(toFind.getId())));
		this.unauthenticate();
	}

	@Test
	public void testSave() {
		final List<Tag> tags2 = this.tagRepository.findAll();
		Tag toFind = new Tag();
		for (final Tag t : tags2)
			if (t.getTagValues().isEmpty()) {
				toFind = t;
				break;
			}
		toFind.setName("Hola");
		this.tagService.save(toFind);
		Assert.notNull(toFind);
		Assert.isTrue(toFind.getName().equals("Hola"));
		this.unauthenticate();
	}

}
