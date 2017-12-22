
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.TagValue;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TagValueServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private TagValueService	tagValueService;


	// Supporting services -----------------

	// Tests -------------------------------

	@Test
	public void testCreate() {
		TagValue tagValue;

		this.authenticate("admin");

		final Trip trip = new Trip();
		final Collection<TagValue> tagValues = new ArrayList<TagValue>();
		trip.setTagValues(tagValues);

		tagValue = this.tagValueService.create(trip);

		Assert.isNull(tagValue.getValue());
		Assert.notNull(tagValue.getTrip());
		Assert.isTrue(tagValue.getTrip().equals(trip));
		Assert.isNull(tagValue.getTag());

		this.unauthenticate();
	}

	@Test
	public void testFindAll() {
		// REVISAR !!!
		// Cómo se comprueba que el findAll() funciona correctamente?

		final Integer currentNumberOfTagValuesInTheXML = 2;

		this.authenticate("admin");

		final Collection<TagValue> tagValues = this.tagValueService.findAll();

		Assert.notNull(tagValues);
		Assert.isTrue(tagValues.size() == currentNumberOfTagValuesInTheXML);

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {
		TagValue tagValue1 = null;
		TagValue tagValue2 = null;

		this.authenticate("admin");

		final Collection<TagValue> tagValues = this.tagValueService.findAll();

		for (final TagValue tv : tagValues)
			if (tv != null) {
				tagValue1 = tv;
				break;
			}

		tagValue2 = this.tagValueService.findOne(tagValue1.getId());

		Assert.isTrue(tagValue1.equals(tagValue2));

		this.unauthenticate();
	}

	// REVISAR !!!
	// No se puede editar un tagValue (método save)

	@Test
	public void testDelete() {
		TagValue tagValue = null;

		this.authenticate("admin");

		final Collection<TagValue> tagValues = this.tagValueService.findAll();

		for (final TagValue tv : tagValues)
			if (tv != null) {
				tagValue = tv;
				break;
			}

		this.tagValueService.delete(tagValue);
		Assert.isNull(this.tagValueService.findOne(tagValue.getId()));

		this.unauthenticate();
	}
}
