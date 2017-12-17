
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

import repositories.CategoryRepository;
import utilities.AbstractTest;
import domain.Category;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private CategoryService		categoryService;

	// Supporting repositories -------------

	@Autowired
	private CategoryRepository	categoryRepository;

	// Supporting services -----------------

	@Autowired
	private TripService			tripService;


	// Tests -------------------------------

	//	@Test
	//	public void testCreate() {
	//		Category category;
	//
	//		this.authenticate("admin1");
	//
	//		category = this.categoryService.create();
	//
	//		Assert.isNull(category.getName());
	//
	//		Assert.notNull(category.getTrips());
	//		Assert.isTrue(category.getTrips().isEmpty());
	//
	//		Assert.notNull(category.getParentCategory());
	//		Assert.isTrue(category.getParentCategory().getName().equals("CATEGORY"));
	//
	//		Assert.notNull(category.getChildCategories());
	//		Assert.isTrue(category.getChildCategories().isEmpty());
	//
	//		this.unauthenticate();
	//	}

	@Test
	public void testSave() {
		Category category = null;
		Collection<Category> categories;
		Collection<Trip> trips;
		Trip trip = null;

		this.authenticate("admin1");

		categories = this.categoryService.findAll();

		int i = 0;
		for (final Category c : categories) {
			i += 1;
			if (i == 2) {
				category = c;
				break;
			}

		}

		trips = this.tripService.findAll();

		for (final Trip t : trips)
			if (!t.getCategory().equals(category)) {
				trip = t;
				break;
			}

		final String name = "Name";

		category.setName(name);
		category.getTrips().add(trip);

		final Category categoryS = this.categoryService.save(category);

		Assert.isTrue(categoryS.getName().equals(name));
		Assert.isTrue(categoryS.getTrips().contains(trip));
		Assert.isTrue(trip.getCategory().equals(category));

		this.unauthenticate();
	}

	@Test
	public void testDelete() {
		Category category = null;

		this.authenticate("admin1");

		int i = 0;
		for (final Category c : this.categoryService.findAll()) {
			i += 1;
			if (i == 2) {
				category = c;
				break;
			}

		}

		this.categoryService.delete(category);

		this.unauthenticate();

	}

	@Test
	public void testFindAll() {
		this.authenticate("admin1");

		final Collection<Category> categories = this.categoryRepository.findAll();
		final Collection<Category> categories2 = this.categoryService.findAll();

		Assert.isTrue(categories2.containsAll(categories) && categories2.size() == categories.size());

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {
		this.authenticate("admin1");

		final List<Category> categories = this.categoryRepository.findAll();
		final Category category = categories.get(0);

		Assert.isTrue(category.equals(this.categoryService.findOne(category.getId())));

		this.unauthenticate();
	}
}
