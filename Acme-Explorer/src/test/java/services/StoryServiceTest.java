
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

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
	private TripService				tripService;

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
		Assert.isTrue(testStory.getAttachments().isEmpty());
		Assert.isTrue(explorer.equals(testStory.getExplorer()));

		this.unauthenticate();

	}

	@Test
	public void testSave() {

		this.authenticate("explorer1");

		Trip tripAccepted = null;
		final Date today = new Date();

		for (final TripApplication ta : this.tripApplicationService.findAcceptedByCurrentExplorer())
			if (ta.getTrip().getEndingDate().after(today)) {
				tripAccepted = ta.getTrip();
				break;
			}

		final Story testStory = this.storyService.create();
		testStory.setTrip(tripAccepted);
		testStory.setText("I was amazed by that marvelous waterfall");
		testStory.setTitle("The marvelous Waterfall");
		testStory.setAttachments(Arrays.asList("http://www.photos.com/marvelous.png"));

		final Story savedStory = this.storyService.save(testStory);

		Assert.notNull(savedStory);
		Assert.isTrue(testStory.getExplorer().equals(savedStory.getExplorer()));
		Assert.isTrue(testStory.getTrip().equals(savedStory.getTrip()));
		Assert.isTrue(testStory.getText().equals(savedStory.getText()));
		Assert.isTrue(testStory.getTitle().equals(savedStory.getTitle()));
		Assert.isTrue(testStory.getAttachments().equals(savedStory.getAttachments()));

		Assert.notNull(this.storyService.findAll().contains(savedStory));

		this.unauthenticate();

	}

	@Test
	public void testFindOne() {

		this.authenticate("explorer1");
		//Explorer1 has written a Story

		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);

		final List<Story> explorerStories = new ArrayList<Story>(explorer.getStories());
		final Story story = explorerStories.get(0);
		final Story foundStory = this.storyService.findOne(story.getId());

		Assert.notNull(foundStory);
		Assert.isTrue(story.equals(foundStory));

		this.unauthenticate();

	}

	@Test
	public void testFindAll() {

		this.authenticate("admin1");

		final Collection<Story> allStories = new HashSet<Story>();

		for (final Trip t : this.tripService.findAll())
			allStories.addAll(t.getStories());

		final Collection<Story> foundStories = this.storyService.findAll();

		Assert.notNull(foundStories);
		Assert.isTrue(allStories.containsAll(foundStories));
		Assert.isTrue(foundStories.containsAll(allStories));
		Assert.isTrue(allStories.size() == foundStories.size());

		this.unauthenticate();

	}
	@Test(expected = IllegalArgumentException.class)
	public void testModify() {

		this.authenticate("explorer1");

		Trip tripAccepted = null;
		final Date today = new Date();

		for (final TripApplication ta : this.tripApplicationService.findAcceptedByCurrentExplorer())
			if (ta.getTrip().getEndingDate().after(today)) {
				tripAccepted = ta.getTrip();
				break;
			}

		final Story testStory = this.storyService.create();
		testStory.setTrip(tripAccepted);
		testStory.setText("I was amazed by that marvelous waterfall");
		testStory.setTitle("The marvelous Waterfall");
		testStory.setAttachments(Arrays.asList("http://www.photos.com/marvelous.png"));

		final Story savedStory = this.storyService.save(testStory);

		savedStory.setText("Modified!!");

		this.storyService.save(savedStory);

		this.unauthenticate();

	}
}
