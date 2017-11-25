
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ExplorerRepository;
import security.Authority;
import security.UserAccount;
import domain.Contact;
import domain.Explorer;
import domain.Finder;
import domain.Folder;
import domain.Message;
import domain.SocialID;
import domain.Story;
import domain.SurvivalClass;
import domain.TripApplication;

@Service
@Transactional
public class ExplorerService {

	/* Repository */
	@Autowired
	ExplorerRepository	explorerRepository;

	/* Services */
	@Autowired
	FolderService		folderService;


	public Explorer create() {

		Explorer explorer;
		UserAccount userAccount;
		final List<SocialID> socialIDs = new ArrayList<SocialID>();
		final List<Message> sentMessages = new ArrayList<Message>();
		final List<Message> receivedMessages = new ArrayList<Message>();
		final List<Authority> authorities = new ArrayList<Authority>();
		final List<SurvivalClass> survivalClasses = new ArrayList<SurvivalClass>();
		final List<Story> stories = new ArrayList<Story>();
		final List<TripApplication> tripApplications = new ArrayList<TripApplication>();
		final List<Contact> emergencyContacts = new ArrayList<Contact>();
		Authority authority;

		explorer = new Explorer();

		explorer.setIsSuspicious(false);
		explorer.setIsBanned(false);
		explorer.setSocialIDs(socialIDs);

		final Collection<Folder> systemFolders = this.folderService.createSystemFolders(explorer);
		explorer.setFolders(systemFolders);
		explorer.setSentMessages(sentMessages);
		explorer.setReceivedMessages(receivedMessages);
		explorer.setStories(stories);
		explorer.setSurvivalClasses(survivalClasses);
		explorer.setTripApplications(tripApplications);
		explorer.setEmergencyContacts(emergencyContacts);

		userAccount = new UserAccount();

		authority = new Authority();
		authority.setAuthority(Authority.EXPLORER);
		authorities.add(authority);

		userAccount.setAuthorities(authorities);
		
		explorer.setUserAccount(userAccount);

		return explorer;
	}

	public Explorer save(final Explorer explorer) {
		Assert.notNull(explorer);
		return this.explorerRepository.save(explorer);
	}

	public Explorer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		final Explorer explorer = this.explorerRepository.findByUserAccountId(userAccount.getId());
		return explorer;
	}

}
