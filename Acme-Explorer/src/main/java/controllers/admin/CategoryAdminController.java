
package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("/category/admin")
public class CategoryAdminController extends AbstractController {

	// Services -------------------------------

	@Autowired
	private CategoryService	categoryService;


	// Constructors ---------------------------

	public CategoryAdminController() {
		super();
	}

	// Display --------------------------------

	// Listing --------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer rootCategoryId) {
		final ModelAndView res;
		final Category rootCategory;

		if (rootCategoryId == null)
			rootCategory = this.categoryService.getParentRootCategory();
		else {
			rootCategory = this.categoryService.findOne(rootCategoryId);
			Assert.notNull(rootCategory);
		}

		res = new ModelAndView("category/list");
		res.addObject("rootCategory", rootCategory);
		res.addObject("actorWS", "admin/");

		return res;
	}

	// Creation -------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int parentCategoryId) {
		ModelAndView res;
		final Category category;
		final Category parentCategory;

		parentCategory = this.categoryService.findOne(parentCategoryId);
		category = this.categoryService.create(parentCategory);
		res = this.createEditModelAndView(category);

		return res;
	}

	// Edition --------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView res;
		final Category category;

		category = this.categoryService.findOne(categoryId);
		Assert.notNull(category);

		res = this.createEditModelAndView(category);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Category category, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(category);
		else
			try {
				this.categoryService.save(category);
				res = new ModelAndView("redirect:/category/admin/list.do?parentCategoryId=" + category.getParentCategory().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(category, "category.commit.error");
			}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Category category, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(category);
		else
			try {
				final int pCategoryId = category.getParentCategory().getId();
				this.categoryService.delete(category);
				res = new ModelAndView("redirect:/category/admin/list.do?parentCategoryId=" + pCategoryId);
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(category, "category.commit.error");
			}

		return res;
	}

	// Ancillary methods ----------------------

	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView res;

		res = this.createEditModelAndView(category, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final Category category, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("category/edit");
		res.addObject("category", category);
		res.addObject("messageCode", messageCode);

		return res;
	}

}
