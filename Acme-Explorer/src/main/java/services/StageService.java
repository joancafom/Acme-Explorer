
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.StageRepository;
import domain.Stage;
import domain.Trip;

@Service
@Transactional
public class StageService {

	// Managed repository ------------------

	@Autowired
	private StageRepository	stageRepository;


	// Supporting services -----------------

	// Constructors ------------------------

	public StageService() {
		super();
	}

	// Simple CRUD methods -----------------

	// Other business methods --------------

	public Stage create(final Trip trip) {
		// REVISAR !!!
		// Cómo solucionar la creación de relaciones OneToOne?
		// Un manager puede crear stages de Trips que no son suyos?
		Assert.notNull(trip);

		Stage stage;
		stage = new Stage();

		stage.setPrice(0.0);

		int number = 1;

		if (!trip.getStages().isEmpty())
			for (int i = 0; i < trip.getStages().size() + 1; i++)
				number += 1;

		stage.setNumber(number);
		stage.setTrip(trip);
		trip.getStages().add(stage);

		return stage;
	}

	public Collection<Stage> findAll() {
		Collection<Stage> stages;

		Assert.notNull(this.stageRepository);
		stages = this.stageRepository.findAll();
		Assert.notNull(stages);

		return stages;
	}

	public Stage findOne(final int stageId) {
		// REVISAR !!!
		// Debe tener algún assert?
		Stage stage;

		stage = this.stageRepository.findOne(stageId);

		return stage;
	}

	public Stage save(final Stage stage) {
		Assert.notNull(stage);

		final Date currentDate = new Date();
		Assert.isTrue(stage.getTrip().getPublicationDate().after(currentDate));

		return this.stageRepository.save(stage);
	}

	public void delete(final Stage stage) {
		Assert.notNull(stage);

		final Date currentDate = new Date();
		Assert.isTrue(stage.getTrip().getPublicationDate().after(currentDate));

		Assert.isTrue(this.stageRepository.exists(stage.getId()));

		this.stageRepository.delete(stage);
	}

}
