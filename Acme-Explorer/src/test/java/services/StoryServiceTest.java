
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Explorer;
import domain.Story;
import domain.Trip;
import domain.TripApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class StoryServiceTest extends AbstractTest {

	//Managed Service 
	@Autowired
	private StoryService			storyService;

	//External Services
	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private TripApplicationService	tripApplicationService;


	@Test
	public void testCreate() {

		this.authenticate("explorer1");

		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);

		final Story testStory = this.storyService.create();

		Assert.notNull(testStory);
		Assert.isNull(testStory.getTitle());
		Assert.isNull(testStory.getText());
		Assert.notNull(testStory.getAttachments());
		Assert.notNull(testStory.getExplorer());
		Assert.notNull(testStory.getTrip());
		Assert.isTrue(testStory.getAttachments().isEmpty());
		Assert.isTrue(explorer.equals(testStory.getExplorer()));

		this.unauthenticate();

	}

	@Test
	public void testSave() {

		this.authenticate("explorer1");

		final Trip tripAccepted;
		final Date today = new Date();

		for (final TripApplication ta : this.tripApplicationService.findAcceptedByCurrentExplorer())
			if (ta.getTrip().getEndingDate().after(today)) {
				tripAccepted = ta.getTrip();
				break;
			}

		this.unauthenticate();

	}
}
