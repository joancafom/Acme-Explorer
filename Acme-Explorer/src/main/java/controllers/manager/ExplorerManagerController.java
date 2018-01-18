
package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SurvivalClassService;
import controllers.AbstractController;
import domain.Explorer;
import domain.SurvivalClass;

@Controller
@RequestMapping("/explorer/manager/")
public class ExplorerManagerController extends AbstractController {

	/* Services */
	@Autowired
	private SurvivalClassService	survivalClassService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int survivalClassId) {
		ModelAndView result;
		Collection<Explorer> explorers;
		final SurvivalClass survivalClass = this.survivalClassService.findOne(survivalClassId);
		Assert.notNull(survivalClass);

		explorers = survivalClass.getExplorers();
		result = new ModelAndView("explorer/listExplorers");
		result.addObject("explorers", explorers);

		return result;
	}

}
