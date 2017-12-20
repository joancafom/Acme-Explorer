
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Admin;
import domain.Auditor;
import domain.Folder;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FolderServiceTest extends AbstractTest {

	// Service under test ------------------

	@Autowired
	private FolderService	folderService;

	// Supporting services -----------------

	@Autowired
	private AdminService	adminService;

	@Autowired
	private AuditorService	auditorService;


	// Tests -------------------------------

	@Test
	public void testCreate() {
		Folder folder;

		this.authenticate("admin1");

		final Admin admin = this.adminService.findByUserAccount(LoginService.getPrincipal());

		folder = this.folderService.create(admin, null);

		Assert.notNull(folder);
		Assert.isTrue(!folder.getIsSystem());
		Assert.isNull(folder.getName());
		Assert.notNull(folder.getMessages());
		Assert.isTrue(folder.getMessages().isEmpty());
		Assert.isNull(folder.getParentFolder());
		Assert.notNull(folder.getChildFolders());
		Assert.isTrue(folder.getChildFolders().isEmpty());
		Assert.notNull(folder.getActor());
		Assert.isTrue(folder.getActor().equals(admin));

		this.unauthenticate();
	}

	@Test
	public void testFindAll() {
		// REVISAR !!!
		// Cómo se comprueba que el findAll() funciona correctamente?

		final Integer currentNumberOfFoldersInTheXML = 32;

		this.authenticate("admin1");

		final Collection<Folder> folders = this.folderService.findAll();

		Assert.notNull(folders);
		Assert.isTrue(folders.size() == currentNumberOfFoldersInTheXML);

		this.unauthenticate();
	}

	@Test
	public void testFindOne() {
		Folder folder1 = null;
		Folder folder2 = null;

		this.authenticate("admin2");

		final Collection<Folder> folders = this.folderService.findAll();

		for (final Folder f : folders)
			if (f != null) {
				folder1 = f;
				break;
			}

		folder2 = this.folderService.findOne(folder1.getId());

		Assert.isTrue(folder1.equals(folder2));

		this.unauthenticate();
	}

	@Test
	public void testSave() {
		// REVISAR !!!
		// Qué se debe comprobar en el save?
		Folder folder1 = null;
		Folder folder2 = null;

		this.authenticate("admin1");

		final Collection<Folder> folders = this.folderService.findAll();

		for (final Folder f : folders)
			if (f != null && f.getActor().getUserAccount().equals(LoginService.getPrincipal())) {
				folder1 = f;
				break;
			}

		// No se puede cambiar el atributo isSystem
		folder1.setName("Name");
		folder1.getMessages().add(new Message());
		folder1.setParentFolder(new Folder());
		folder1.getChildFolders().add(new Folder());
		// No se puede cambiar el atributo actor

		folder2 = this.folderService.save(folder1);

		Assert.notNull(folder2);
		Assert.isTrue(folder1.getName().equals(folder2.getName()));
		Assert.isTrue(folder1.getMessages().equals(folder2.getMessages()));
		Assert.isTrue(folder1.getParentFolder().equals(folder2.getParentFolder()));
		Assert.isTrue(folder1.getChildFolders().equals(folder2.getChildFolders()));

		this.unauthenticate();
	}

	//	@Test
	//	public void testCreateSystemFolders() {
	//		final Collection<Folder> systemFolders;
	//
	//		this.authenticate("admin1");
	//
	//		final Admin admin = this.adminService.findByUserAccount(LoginService.getPrincipal());
	//
	//		systemFolders = this.folderService.createSystemFolders(admin);
	//
	//		Assert.notNull(systemFolders);
	//
	//		final List<String> systemFolderNames = Arrays.asList("In Box", "Out Box", "Notification Box", "Trash Box", "Spam Box");
	//		final List<String> foldersNames = new ArrayList<String>();
	//
	//		Assert.notNull(systemFolders);
	//		Assert.isTrue(systemFolders.size() == 5);
	//
	//		for (final Folder f : systemFolders) {
	//			foldersNames.add(f.getName());
	//
	//			Assert.isTrue(f.getIsSystem());
	//
	//			Assert.notNull(f.getMessages());
	//			Assert.isTrue(f.getMessages().isEmpty());
	//
	//			Assert.isNull(f.getParentFolder());
	//
	//			Assert.notNull(f.getChildFolders());
	//			Assert.isTrue(f.getChildFolders().isEmpty());
	//
	//			Assert.isTrue(f.getActor().equals(admin));
	//		}
	//
	//		Assert.isTrue(systemFolderNames.equals(foldersNames));
	//
	//		this.unauthenticate();
	//	}

	@Test
	public void testFindAllByPrincipal() {
		// REVISAR !!!
		// Cómo se comprueba que el findAll() funciona correctamente?

		this.authenticate("admin1");

		final Collection<Folder> folders1 = this.folderService.findAllByPrincipal();
		final Collection<Folder> folders2 = this.folderService.findAll();
		final Collection<Folder> foldersRemove = new ArrayList<Folder>();

		for (final Folder f : folders2)
			if (!f.getActor().getUserAccount().equals(LoginService.getPrincipal()))
				foldersRemove.add(f);

		folders2.removeAll(foldersRemove);

		Assert.notNull(folders1);
		Assert.isTrue(folders1.equals(folders2));

		this.unauthenticate();
	}

	@Test
	public void testFindOneByPrincipal() {
		Folder folder1 = null;
		Folder folder2 = null;

		this.authenticate("admin1");

		final Collection<Folder> folders = this.folderService.findAll();

		for (final Folder f : folders)
			if (f != null) {
				folder1 = f;
				break;
			}

		folder2 = this.folderService.findOneByPrincipal(folder1.getId());

		Assert.isTrue(folder1.equals(folder2));

		this.unauthenticate();
	}

	@Test
	public void testDeleteByPrincipal() {
		Folder folder = null;

		this.authenticate("admin1");

		final Collection<Folder> folders = this.folderService.findAll();

		for (final Folder f : folders)
			if (f.getActor().getUserAccount().equals(LoginService.getPrincipal())) {
				folder = f;
				break;
			}

		this.folderService.deleteByPrincipal(folder);

		Assert.isNull(this.folderService.findOne(folder.getId()));

		this.unauthenticate();
	}

	@Test
	public void testFindByActorAndName() {
		this.authenticate("auditor1");

		final Auditor auditor = this.auditorService.findByUserAccount(LoginService.getPrincipal());
		final Folder folder = this.folderService.create(auditor, null);
		folder.setName("Mis Cosas");

		final Folder savedFolder = this.folderService.save(folder);

		final Folder foundFolder = this.folderService.findByActorAndName(auditor, "Mis Cosas");

		Assert.notNull(foundFolder);
		Assert.isTrue(savedFolder.equals(foundFolder));

		this.unauthenticate();

	}

}
