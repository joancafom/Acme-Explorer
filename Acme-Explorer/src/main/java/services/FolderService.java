
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Admin;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class FolderService {

	// Managed repository ------------------

	@Autowired
	private FolderRepository	folderRepository;

	// Supporting services -----------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private AdminService		adminService;


	// Constructors ------------------------

	public FolderService() {
		super();
	}

	// Simple CRUD methods -----------------

	public Folder create(final Actor actor) {
		Folder folder;
		UserAccount userAccount;
		final List<Message> messages = new ArrayList<Message>();
		final List<Folder> childFolders = new ArrayList<Folder>();

		userAccount = LoginService.getPrincipal();
		final Admin admin = this.adminService.findByUserAccount(userAccount);
		Assert.isTrue(admin != null || userAccount.equals(actor.getUserAccount()));

		folder = new Folder();
		folder.setIsSystem(false);
		folder.setActor(actor);
		folder.setMessages(messages);
		folder.setChildFolders(childFolders);

		if (actor.getFolders() != null)
			actor.getFolders().add(folder);

		return folder;
	}

	public Collection<Folder> findAll() {
		final UserAccount userAccount;
		final Collection<Folder> folders;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		folders = this.folderRepository.findAllFoldersOfActor(this.actorService.findByUserAccount(userAccount).getId());

		return folders;
	}

	public Folder findOne(final int folderId) {
		UserAccount userAccount;
		Folder folder;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		folder = this.folderRepository.findOne(folderId);
		Assert.notNull(folder);
		Assert.isTrue(userAccount.equals(folder.getActor().getUserAccount()));

		return folder;
	}

	public Folder save(final Folder folder) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.notNull(folder);
		Assert.isTrue(userAccount.equals(folder.getActor().getUserAccount()));
		Assert.isTrue(!folder.getIsSystem());

		return this.folderRepository.save(folder);
	}

	public void delete(final Folder folder) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.notNull(folder);
		Assert.isTrue(userAccount.equals(folder.getActor().getUserAccount()));
		Assert.isTrue(!folder.getIsSystem());

		this.folderRepository.delete(folder);
	}

	// Other business methods --------------

	public Collection<Folder> createSystemFolders(final Actor actor) {
		final String[] sysFolderNames = {
			"In Box", "Out Box", "Notification Box", "Trash Box", "Spam Box"
		};

		final List<Folder> res = new ArrayList<Folder>();

		for (final String s : sysFolderNames) {
			final Folder f = this.create(actor);
			f.setIsSystem(true);
			f.setName(s);
			f.setActor(actor);
			res.add(f);
		}
		return res;
	}

	public Collection<Folder> findAllGivenActor(final Actor actor) {
		Collection<Folder> folders;

		folders = this.folderRepository.findAllFoldersOfActor(actor.getId());

		return folders;

	}

	public Folder findOneGivenActor(final int folderID, final Actor actor) {
		Folder folder;

		folder = this.folderRepository.findOne(folderID);

		return folder;
	}

	public Folder findByActorAndName(final Actor actor, final String name) {
		Folder folder;

		Assert.notNull(actor);
		Assert.notNull(name);

		folder = this.folderRepository.findByActorIdAndName(actor.getId(), name);

		Assert.notNull(folder);

		return folder;
	}
}
