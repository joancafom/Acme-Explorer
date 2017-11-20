
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;
import domain.Trip;

@Service
@Transactional
public class CategoryService {

	// Managed repository ------------------

	@Autowired
	private CategoryRepository	categoryRepository;


	// Supporting services -----------------

	// Constructors ------------------------

	public CategoryService() {
		super();
	}

	// Simple CRUD methods -----------------

	public Category create() {
		final Category category;

		category = new Category();

		category.setParentCategory(this.categoryRepository.findByName("CATEGORY"));
		category.setTrips(new HashSet<Trip>());
		category.setChildCategories(new HashSet<Category>());

		return category;
	}

	public Collection<Category> findAll() {
		final Collection<Category> categories;

		categories = this.categoryRepository.findAll();
		Assert.notNull(categories);

		return categories;
	}

	public Category findOne(final int categoryId) {
		final Category category;

		category = this.categoryRepository.findOne(categoryId);
		Assert.notNull(category);

		return category;
	}

	public Category save(final Category category) {

		Assert.notNull(category);

		Boolean res = false;

		Assert.notNull(category.getParentCategory());

		for (final Category c : category.getParentCategory().getChildCategories())
			if (c.getName().equals(category.getName()) && !c.equals(category)) {
				res = true;
				break;
			}

		Assert.isTrue(!res);
		Assert.isTrue(!category.getName().equals("CATEGORY"));

		for (final Trip t : category.getTrips())
			t.setCategory(category);

		return this.categoryRepository.save(category);
	}

	public void delete(final Category category) {

		Assert.notNull(category);
		Assert.isTrue(this.categoryRepository.exists(category.getId()));

		this.categoryRepository.delete(category);
	}

	// Other business methods --------------

}
