
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
import domain.Trip;
import domain.TripApplication;

@Service
@Transactional
public class StoryService {

	//Managed Repository
	@Autowired
	private StoryRepository			storyRepository;

	//Supporting Services
	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private TripApplicationService	tripApplicationService;


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
		Assert.isTrue(userAccount.equals(story.getExplorer().getUserAccount()));

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

		return this.storyRepository.save(story);

	}
}
