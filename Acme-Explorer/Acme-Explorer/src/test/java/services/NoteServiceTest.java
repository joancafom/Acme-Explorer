
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Auditor;
import domain.Category;
import domain.LegalText;
import domain.Note;
import domain.Ranger;
import domain.Stage;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NoteServiceTest extends AbstractTest {

	//Service under test
	@Autowired
	private NoteService			noteService;

	//Other required Services

	@Autowired
	private AuditorService		auditorService;

	@Autowired
	private TripService			tripService;

	@Autowired
	private LegalTextService	legalTextService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private RangerService		rangerService;

	//Working Variables
	Trip						trip1;
	Stage						stageBronx;
	Collection<Stage>			stagesNYC;
	LegalText					legalTextBronx;
	Category					categoryNYC;
	Ranger						rangerNYC;


	@Before
	public void setUpWorkingVariables() {

		this.authenticate("admin1");

		this.stageBronx = new Stage();
		this.stageBronx.setNumber(1);
		this.stageBronx.setDescription("We will explore the Bronx going by bus throughout its streets");
		this.stageBronx.setPrice(60.0);
		this.stageBronx.setTitle("Discover the Bronx");

		this.stagesNYC = new ArrayList<Stage>();
		this.stagesNYC.add(this.stageBronx);

		this.legalTextBronx = this.legalTextService.create();
		this.legalTextBronx.setBody("The possesion of gunfires as well as other weapons is strictly forbidden in this trip");
		this.legalTextBronx.setIsFinal(false);
		this.legalTextBronx.setNumberOfApplicableLaws("130");
		this.legalTextBronx.setTitle("Weapons Notice");

		this.categoryNYC = this.categoryService.create();
		this.categoryNYC.setName("New York City");

		this.rangerNYC = this.rangerService.create();
		this.rangerNYC.setAddress("C/ Zarzamora 13, 41900 Sevilla");
		this.rangerNYC.setEmail("imenorque3nyc@gmail.com");
		this.rangerNYC.setName("Icaro");
		this.rangerNYC.setSurname("Thomson");

		this.authenticate("manager1");
		this.trip1 = this.tripService.create(this.stagesNYC, this.legalTextBronx, this.categoryNYC, this.rangerNYC);
	}

	@Test
	public void testCreate() {

		this.authenticate("auditor1");

		final Auditor me = this.auditorService.findByUserAccount(LoginService.getPrincipal());

		final Note testNote = this.noteService.create(this.trip1);
		Assert.isNull(testNote.getRemark());
		Assert.notNull(testNote.getWrittenMoment());
		Assert.isTrue(testNote.getWrittenMoment().before(new Date()));
		Assert.isNull(testNote.getReply());
		Assert.isNull(testNote.getReplyMoment());
		Assert.isTrue(testNote.getAuditor().equals(me));
		Assert.isTrue(testNote.getTrip().equals(this.trip1));

		this.unauthenticate();
	}

}
