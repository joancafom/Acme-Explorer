
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Tag;
import domain.TagValue;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TagValueServiceTest extends AbstractTest {

	//Service under test
	@Autowired
	private TagValueService	tagValueService;

	//Other required Services
	@Autowired
	private TripService		tripService;

	@Autowired
	private TagService		tagService;

	//Working Variables
	private Trip			trip1;
	private Tag				tag1;


	@Before
	public void setUpWorkingVariables() {

		this.authenticate("admin1");

		final List<Trip> allTrips = new ArrayList<Trip>(this.tripService.findAll());
		this.trip1 = allTrips.get(0);

		final Tag tagPre = this.tagService.create();
		tagPre.setName("Fnac");
		this.tag1 = this.tagService.save(tagPre);

		this.unauthenticate();
	}

	@Test
	public void testCreate() {

		this.authenticate("admin1");

		final TagValue testTagValue = this.tagValueService.create(this.trip1, this.tag1);

		Assert.notNull(testTagValue);
		Assert.isNull(testTagValue.getValue());
		Assert.notNull(testTagValue.getTag());
		Assert.isTrue(testTagValue.getTag().equals(this.tag1));
		Assert.notNull(testTagValue.getTrip());
		Assert.isTrue(testTagValue.getTrip().equals(this.trip1));

		this.unauthenticate();
	}

	@Test
	public void testDelete() {

		this.authenticate("admin1");

		final List<TagValue> tagValuesOfTrip = new ArrayList<TagValue>(this.trip1.getTagValues());

		final TagValue tagValueToRemove = tagValuesOfTrip.get(0);

		this.tagValueService.delete(tagValueToRemove);

		this.unauthenticate();
	}
}
