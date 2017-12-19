
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LegalTextRepository;
import domain.LegalText;
import domain.Trip;

@Service
@Transactional
public class LegalTextService {

	// Managed repository ------------------

	@Autowired
	private LegalTextRepository	legalTextRepository;


	// Supporting services -----------------

	// Constructors ------------------------

	public LegalTextService() {
		super();
	}

	// Simple CRUD methods -----------------

	public LegalText create() {
		LegalText legalText;
		long millis;
		Date registrationMoment;
		final List<Trip> trips = new ArrayList<Trip>();

		legalText = new LegalText();

		millis = System.currentTimeMillis() - 1000;
		registrationMoment = new Date(millis);

		legalText.setRegistrationMoment(registrationMoment);
		legalText.setIsFinal(false);
		legalText.setTrips(trips);

		return legalText;
	}

	public Collection<LegalText> findAll() {
		Collection<LegalText> legalTexts;

		Assert.notNull(this.legalTextRepository);
		legalTexts = this.legalTextRepository.findAll();
		Assert.notNull(legalTexts);

		return legalTexts;
	}

	public LegalText findOne(final int legalTextId) {
		LegalText legalText;

		legalText = this.legalTextRepository.findOne(legalTextId);

		return legalText;
	}

	public LegalText save(final LegalText legalText) {

		Assert.notNull(legalText);
		if (legalText.getId() != 0)
			Assert.isTrue(!this.legalTextRepository.findOne(legalText.getId()).getIsFinal());
		if (!legalText.getTrips().isEmpty())
			Assert.isTrue(legalText.getIsFinal());

		return this.legalTextRepository.save(legalText);

	}

	public void delete(final LegalText legalText) {
		Assert.notNull(legalText);

		Assert.isTrue(this.legalTextRepository.exists(legalText.getId()));
		Assert.isTrue(!legalText.getIsFinal());

		this.legalTextRepository.delete(legalText);
	}

	// Other business methods --------------

}
