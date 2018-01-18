
package controllers.explorer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("/category/explorer")
public class CategoryExplorerController extends AbstractController {

	//Services
	@Autowired
	private CategoryService	categoryService;


	//List
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
		res.addObject("actorWS", "explorer/");

		return res;
	}
}
