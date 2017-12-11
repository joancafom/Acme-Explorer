
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.StoryRepository;
import security.LoginService;
import security.UserAccount;
import domain.Explorer;
import domain.Story;
import domain.SystemConfiguration;
import domain.Trip;
import domain.TripApplication;

@Service
@Transactional
public class StoryService {

	//Managed Repository
	@Autowired
	private StoryRepository				storyRepository;

	//Supporting Services
	@Autowired
	private ExplorerService				explorerService;

	@Autowired
	private TripApplicationService		tripApplicationService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	//Simple CRUD operations

	public Story create() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);
		Assert.notNull(explorer);

		final Story res = new Story();

		res.setAttachments(new ArrayList<String>());
		res.setExplorer(explorer);

		explorer.getStories().add(res);

		return res;
	}

	public Story findOne(final int storyId) {

		return this.storyRepository.findOne(storyId);
	}

	public Collection<Story> findAll() {

		return this.storyRepository.findAll();
	}

	public Story save(final Story story) {

		Assert.notNull(story);
		Assert.notNull(story.getTrip());

		//An Explorer cannot modify a story
		Assert.isTrue(story.getId() == 0);

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);
		Assert.isTrue(story.getExplorer().equals(explorer));

		//We check that the Explorer has an accepted TripApplication for that Trip

		final Trip trip = story.getTrip();

		boolean tripMatched = false;

		for (final TripApplication ta : this.tripApplicationService.findAcceptedByCurrentExplorer())
			if (ta.getTrip().equals(trip)) {
				tripMatched = true;
				break;
			}

		Assert.isTrue(tripMatched);
		Assert.isTrue(trip.getEndingDate().after(new Date()));

		final Boolean isSuspicious;
		isSuspicious = this.decideSuspiciousness(story.getTitle() + " " + story.getText());

		if (isSuspicious)
			explorer.setIsSuspicious(isSuspicious);

		return this.storyRepository.save(story);

	}

	//Other Business Methods

	public Boolean decideSuspiciousness(final String testString) {
		final SystemConfiguration sysConfig = this.systemConfigurationService.getCurrentSystemConfiguration();
		Assert.notNull(sysConfig);

		Boolean res = false;

		for (final String spamWord : sysConfig.getSpamWords())
			if (testString.toLowerCase().contains(spamWord)) {
				res = true;
				break;
			}

		return res;
	}
}
