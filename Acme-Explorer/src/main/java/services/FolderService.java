
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
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
	private MessageService		messageService;


	// Constructors ------------------------

	public FolderService() {
		super();
	}

	// Simple CRUD methods -----------------

	public Folder create(final Actor actor, final Folder parentFolder) {
		Assert.notNull(actor);

		Folder folder;
		//final UserAccount userAccount = LoginService.getPrincipal();

		final List<Message> messages = new ArrayList<Message>();
		final List<Folder> childFolders = new ArrayList<Folder>();

		// REVISAR !!!
		// El método para crear las systemFolders cuando se crea un actor puede ser invocado por alguien no autentificado (registro)
		// Cómo comprobar que no hay nadie autentificado si el método .getPrincipal() tiene un Assert.notNull()

		// Assert.isTrue(userAccount == null || userAccount.equals(actor.getUserAccount()));

		folder = new Folder();
		folder.setIsSystem(false);
		folder.setMessages(messages);
		folder.setChildFolders(childFolders);
		folder.setActor(actor);
		if (parentFolder != null) {
			folder.setParentFolder(parentFolder);
			parentFolder.getChildFolders().add(folder);
		}

		return folder;
	}

	public Collection<Folder> findAll() {
		final Collection<Folder> folders;

		Assert.notNull(this.folderRepository);
		folders = this.folderRepository.findAll();
		Assert.notNull(folders);

		return folders;
	}

	public Folder findOne(final int folderId) {
		// REVISAR !!!
		// Debe tener algún assert?
		Folder folder;

		folder = this.folderRepository.findOne(folderId);

		return folder;
	}

	public Folder save(final Folder folder) {
		Assert.notNull(folder);
		Assert.isTrue(!folder.getIsSystem());
		Assert.isTrue(folder.getActor().getUserAccount().equals(LoginService.getPrincipal()));
		Assert.isTrue(!folder.getName().equals("In Box"));
		Assert.isTrue(!folder.getName().equals("Out Box"));
		Assert.isTrue(!folder.getName().equals("Notification Box"));
		Assert.isTrue(!folder.getName().equals("Trash Box"));
		Assert.isTrue(!folder.getName().equals("Spam Box"));

		return this.folderRepository.save(folder);
	}

	// REVISAR !!!
	// Es necesario hacer el delete?

	// Other business methods --------------

	public Collection<Folder> createSystemFolders(final Actor actor) {
		final String[] sysFolderNames = {
			"In Box", "Out Box", "Notification Box", "Trash Box", "Spam Box"
		};

		final List<Folder> res = new ArrayList<Folder>();

		for (final String s : sysFolderNames) {
			final Folder f = this.create(actor, null);
			f.setIsSystem(true);
			f.setName(s);
			f.setActor(actor);
			res.add(f);
		}
		return res;
	}

	public Collection<Folder> findAllByPrincipal() {
		final Collection<Folder> folders;

		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor actor = this.actorService.findByUserAccount(userAccount);

		Assert.notNull(this.folderRepository);
		folders = this.folderRepository.findAllByActorId(actor.getId());
		Assert.notNull(folders);

		return folders;
	}

	public Folder findOneByPrincipal(final int folderId) {
		// REVISAR !!!
		// Debe tener algún assert?
		Folder folder;

		folder = this.folderRepository.findOne(folderId);

		Assert.isTrue(folder.getActor().getUserAccount().equals(LoginService.getPrincipal()));

		return folder;
	}

	public void deleteByPrincipal(final Folder folder) {
		Assert.notNull(folder);
		Assert.isTrue(!folder.getIsSystem());
		final UserAccount us = LoginService.getPrincipal();
		final Actor actor = this.actorService.findByUserAccount(us);
		Assert.notNull(actor);
		Assert.isTrue(folder.getActor().equals(actor));
		for (final Message m : folder.getMessages())
			this.messageService.delete(m);

		this.folderRepository.delete(folder);
	}
	public Folder findByActorAndName(final Actor actor, final String name) {
		Folder folder;

		Assert.notNull(actor);
		Assert.notNull(name);

		folder = this.folderRepository.findByActorIdAndName(actor.getId(), name);

		return folder;
	}

	public Collection<Folder> findAllParentFoldersByPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Folder> folders = this.findAllByPrincipal();
		final Collection<Folder> res = new HashSet<Folder>();
		for (final Folder f : folders)
			if (f.getParentFolder() == null)
				res.add(f);

		return res;

	}
}
