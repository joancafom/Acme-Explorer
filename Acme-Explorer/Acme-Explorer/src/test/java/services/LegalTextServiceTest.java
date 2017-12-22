
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.LegalText;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class LegalTextServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private LegalTextService	legalTextService;


	// Supporting services -----------------

	// Tests -------------------------------

	@Test
	public void testCreate() {
		LegalText legalText;

		this.authenticate("admin");

		legalText = this.legalTextService.create();

		Assert.isNull(legalText.getTitle());
		Assert.isNull(legalText.getBody());
		Assert.isNull(legalText.getLaws());
		Assert.notNull(legalText.getRegistrationMoment());
		Assert.isTrue(!legalText.getIsFinal());
		Assert.notNull(legalText.getTrips());
		Assert.isTrue(legalText.getTrips().isEmpty());

		this.unauthenticate();
	}

	@Test
	public void testFindAll() {
		// REVISAR !!!
		// Cómo se comprueba que el findAll() funciona correctamente?

		final Integer currentNumberOfLegalTextsInTheXML = 2;

		this.authenticate("admin");

		final Collection<LegalText> legalTexts = this.legalTextService.findAll();

		Assert.notNull(legalTexts);
		Assert.isTrue(legalTexts.size() == currentNumberOfLegalTextsInTheXML);

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {
		LegalText legalText1 = null;
		LegalText legalText2 = null;

		this.authenticate("admin");

		final Collection<LegalText> legalTexts = this.legalTextService.findAll();

		for (final LegalText l : legalTexts)
			if (l != null) {
				legalText1 = l;
				break;
			}

		legalText2 = this.legalTextService.findOne(legalText1.getId());

		Assert.isTrue(legalText1.equals(legalText2));

		this.unauthenticate();
	}

	@Test
	public void testSave() {
		LegalText legalText1 = null;
		LegalText legalText2 = null;

		this.authenticate("admin");

		final Collection<LegalText> legalTexts = this.legalTextService.findAll();

		for (final LegalText l : legalTexts)
			if (l != null && l.getIsFinal() == false) {
				legalText1 = l;
				break;
			}

		legalText1.setTitle("Title");
		legalText1.setBody("Body");
		legalText1.setLaws("3");
		// El registrationMoment no se puede editar

		legalText2 = this.legalTextService.save(legalText1);

		Assert.notNull(legalText2);
		Assert.isTrue(legalText1.getTitle().equals(legalText2.getTitle()));
		Assert.isTrue(legalText1.getBody().equals(legalText2.getBody()));
		Assert.isTrue(legalText1.getLaws().equals(legalText2.getLaws()));

		this.unauthenticate();
	}

	@Test
	public void testDelete() {
		LegalText legalText = null;

		this.authenticate("admin");

		final Collection<LegalText> legalTexts = this.legalTextService.findAll();

		for (final LegalText l : legalTexts)
			if (l != null && l.getIsFinal() == false) {
				legalText = l;
				break;
			}

		this.legalTextService.delete(legalText);

		Assert.isNull(this.legalTextService.findOne(legalText.getId()));

		this.unauthenticate();
	}

}
