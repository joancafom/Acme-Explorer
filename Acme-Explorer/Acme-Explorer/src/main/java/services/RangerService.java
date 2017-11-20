
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RangerRepository;
import security.Authority;
import security.UserAccount;
import domain.Folder;
import domain.Message;
import domain.Ranger;
import domain.SocialID;
import domain.Trip;

@Service
@Transactional
public class RangerService {

	// Managed repository ------------------

	@Autowired
	private RangerRepository	rangerRepository;

	// Supporting services -----------------

	@Autowired
	private FolderService		folderService;


	// Constructors ------------------------

	public RangerService() {
		super();
	}

	// Simple CRUD methods -----------------

	public Ranger create() {
		Ranger ranger;
		final UserAccount userAccountRanger;
		final List<SocialID> socialIDs = new ArrayList<SocialID>();
		final List<Trip> trips = new ArrayList<Trip>();
		final List<Message> sentMessages = new ArrayList<Message>();
		final List<Message> receivedMessages = new ArrayList<Message>();
		final List<Authority> authorities = new ArrayList<Authority>();
		Authority authority;

		ranger = new Ranger();

		ranger.setIsSuspicious(false);
		ranger.setIsBanned(false);
		ranger.setSocialIDs(socialIDs);

		final Collection<Folder> systemFolders = this.folderService.createSystemFolders(ranger);
		ranger.setFolders(systemFolders);

		ranger.setSentMessages(sentMessages);
		ranger.setReceivedMessages(receivedMessages);
		ranger.setTrips(trips);

		userAccountRanger = new UserAccount();

		authority = new Authority();
		authority.setAuthority(Authority.RANGER);
		authorities.add(authority);

		userAccountRanger.setAuthorities(authorities);

		ranger.setUserAccount(userAccountRanger);

		return ranger;
	}

	public Ranger save(final Ranger ranger) {
		Assert.notNull(ranger);

		return this.rangerRepository.save(ranger);
	}

	// Other business methods --------------

	public Collection<Ranger> findAllSuspicious() {
		Collection<Ranger> rangers;

		rangers = this.rangerRepository.findAllSuspicious();

		Assert.notNull(rangers);

		return rangers;
	}

	public Ranger findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		final Ranger ranger = this.rangerRepository.findByUserAccountId(userAccount.getId());
		return ranger;
	}
}
