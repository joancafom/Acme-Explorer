
package controllers.admin;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TagService;
import controllers.AbstractController;
import domain.Tag;

@Controller
@RequestMapping("/tag/admin")
public class TagAdminController extends AbstractController {

	/* Services */

	@Autowired
	private TagService	tagService;


	public TagAdminController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Tag> tags;

		tags = this.tagService.findAll();

		result = new ModelAndView("tag/list");

		result.addObject("tags", tags);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Tag tag;

		tag = this.tagService.create();

		result = this.createEditModelAndView(tag);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tagId) {
		ModelAndView result;
		Tag tag;

		tag = this.tagService.findOne(tagId);
		Assert.notNull(tag);

		result = this.createEditModelAndView(tag);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tag tag, final BindingResult binding) {

		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(tag);
		else
			try {
				this.tagService.save(tag);
				res = new ModelAndView("redirect:/tag/admin/list.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(tag, "tag.commit.error");
			}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Tag tag, final BindingResult binding) {

		ModelAndView res;

		try {
			this.tagService.delete(tag);
			res = new ModelAndView("redirect:/tag/admin/list.do");
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(tag, "tagValue.commit.error");
		}

		return res;
	}

	private ModelAndView createEditModelAndView(final Tag tag) {
		ModelAndView result;
		result = this.createEditModelAndView(tag, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Tag tag, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("tag/edit");
		result.addObject("tag", tag);
		if (tag.getTagValues().isEmpty())
			result.addObject("tagControl", "empty");
		else
			result.addObject("tagControl", "notEmpty");
		return result;
	}
}
