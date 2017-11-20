
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.StageRepository;

@Service
@Transactional
public class StageService {

	// Managed repository ------------------

	@SuppressWarnings("unused")
	@Autowired
	private StageRepository	stageRepository;


	// Supporting services -----------------

	// Constructors ------------------------

	public StageService() {
		super();
	}

	// Simple CRUD methods -----------------

	// Other business methods --------------

}
