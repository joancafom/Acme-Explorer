
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Stage;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class StageServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private StageService	stageService;


	// Supporting services -----------------

	// Tests -------------------------------

	@Test
	public void testCreate() {
		Stage stage;

		this.authenticate("manager1");

		final Trip trip = new Trip();
		final Collection<Stage> stages = new ArrayList<Stage>();
		trip.setStages(stages);

		stage = this.stageService.create(trip);

		Assert.isNull(stage.getTitle());
		Assert.isNull(stage.getDescription());
		Assert.isTrue(stage.getPrice() == 0.0);
		Assert.isTrue(stage.getNumber() == 1);
		Assert.notNull(stage.getTrip());
		Assert.isTrue(stage.getTrip().equals(trip));

		this.unauthenticate();
	}

	@Test
	public void testFindAll() {
		// REVISAR !!!
		// Cómo se comprueba que el findAll() funciona correctamente?

		final Integer currentNumberOfStagesInTheXML = 4;

		this.authenticate("manager1");

		final Collection<Stage> stages = this.stageService.findAll();

		Assert.notNull(stages);
		Assert.isTrue(stages.size() == currentNumberOfStagesInTheXML);

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {
		Stage stage1 = null;
		Stage stage2 = null;

		this.authenticate("manager1");

		final Collection<Stage> stages = this.stageService.findAll();

		for (final Stage s : stages)
			if (s != null) {
				stage1 = s;
				break;
			}

		stage2 = this.stageService.findOne(stage1.getId());

		Assert.isTrue(stage1.equals(stage2));

		this.unauthenticate();
	}

	@Test
	public void testSave() {
		Stage stage1 = null;
		Stage stage2 = null;

		this.authenticate("manager1");

		final Collection<Stage> stages = this.stageService.findAll();

		for (final Stage s : stages)
			if (s != null && s.getTrip().getPublicationDate().after(new Date())) {
				stage1 = s;
				break;
			}

		stage1.setTitle("Title");
		stage1.setDescription("Description");
		stage1.setPrice(500.0);
		// REVISAR
		// Se puede cambiar el orden de una stage? Y el Trip?

		stage2 = this.stageService.save(stage1);

		Assert.notNull(stage2);
		Assert.isTrue(stage1.getTitle().equals(stage2.getTitle()));
		Assert.isTrue(stage1.getDescription().equals(stage2.getDescription()));
		Assert.isTrue(stage1.getPrice() == stage2.getPrice());
	}

	@Test
	public void testDelete() {
		Stage stage = null;

		this.authenticate("manager1");

		final Collection<Stage> stages = this.stageService.findAll();

		for (final Stage s : stages)
			if (s != null && s.getTrip().getPublicationDate().after(new Date())) {
				stage = s;
				break;
			}

		this.stageService.delete(stage);
		Assert.isNull(this.stageService.findOne(stage.getId()));

		this.unauthenticate();
	}
}
