
package services;

import java.util.ArrayList;
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
	@SuppressWarnings("unused")
	@Autowired
	private StoryRepository			storyRepository;

	//Supporting Services
	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private TripApplicationService	tripApplicationService;


	//Simple CRUD operations

	public Story create(final Trip trip) {

		final UserAccount userAccount = LoginService.getPrincipal();

		final Explorer explorer = this.explorerService.findByUserAccount(userAccount);

		boolean tripMatched = false;

		for (final TripApplication ta : this.tripApplicationService.findAcceptedByCurrentExplorer())
			if (ta.getTrip().equals(trip)) {
				tripMatched = true;
				break;
			}

		Assert.isTrue(tripMatched);

		final Story res = new Story();

		res.setAttachments(new ArrayList<String>());
		res.setExplorer(explorer);
		res.setTrip(trip);
		Assert.isTrue(trip.getEndingDate().after(new Date()));

		trip.getStories().add(res);
		explorer.getStories().add(res);

		return res;
	}

}
