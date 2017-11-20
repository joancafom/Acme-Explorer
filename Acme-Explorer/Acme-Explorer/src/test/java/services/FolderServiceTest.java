
package services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Auditor;
import domain.Folder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FolderServiceTest extends AbstractTest {

	//Service under test
	@Autowired
	private FolderService	folderService;

	//Other required Services
	@Autowired
	private AuditorService	auditorService;

	//Working Variables
	private Auditor			auditor1;


	@Before
	public void setUpWorkingVariables() {

		this.authenticate("auditor1");

		this.auditor1 = this.auditorService.findByUserAccount(LoginService.getPrincipal());

		this.unauthenticate();
	}
	@Test
	public void testCreate() {

		this.authenticate("auditor1");

		final Folder testFolder = this.folderService.create(this.auditor1);

		Assert.notNull(testFolder);
		Assert.isTrue(!testFolder.getIsSystem());
		Assert.isNull(testFolder.getName());
		Assert.notNull(testFolder.getMessages());
		Assert.isTrue(testFolder.getMessages().isEmpty());
		Assert.notNull(testFolder.getActor());
		Assert.isTrue(testFolder.getActor().equals(this.auditor1));
		Assert.isNull(testFolder.getParentFolder());
		Assert.notNull(testFolder.getChildFolders());
		Assert.isTrue(testFolder.getChildFolders().isEmpty());
		Assert.isTrue(this.auditor1.getFolders().contains(testFolder));

		this.unauthenticate();

	}

	@Test
	public void testSave() {

		this.authenticate("auditor1");

		final Folder testFolder1 = this.folderService.create(this.auditor1);
		final Folder testFolder2 = this.folderService.create(this.auditor1);

		testFolder1.setName("Mis Foldito");
		testFolder2.setName("Mis Folder");
		testFolder1.setParentFolder(testFolder2);
		testFolder2.getChildFolders().add(testFolder1);

		final Folder testFolder1Saved = this.folderService.save(testFolder1);
		final Folder testFolder2Saved = this.folderService.save(testFolder2);

		Assert.isTrue(testFolder1.getIsSystem() == testFolder1Saved.getIsSystem());
		Assert.isTrue(testFolder1.getActor().equals(testFolder1Saved.getActor()));
		Assert.isTrue(testFolder1.getChildFolders().equals(testFolder1Saved.getChildFolders()));
		Assert.isTrue(testFolder1.getParentFolder().equals(testFolder1Saved.getParentFolder()));
		Assert.isTrue(testFolder1.getMessages().equals(testFolder1Saved.getMessages()));
		Assert.isTrue(testFolder1.getName().equals(testFolder1Saved.getName()));

		Assert.isTrue(testFolder2.getIsSystem() == testFolder2Saved.getIsSystem());
		Assert.isTrue(testFolder2.getActor().equals(testFolder2Saved.getActor()));
		Assert.isTrue(testFolder2.getChildFolders().equals(testFolder2Saved.getChildFolders()));
		Assert.isTrue(testFolder2.getParentFolder() == testFolder2Saved.getParentFolder());
		Assert.isTrue(testFolder2.getMessages().equals(testFolder2Saved.getMessages()));
		Assert.isTrue(testFolder2.getName().equals(testFolder2Saved.getName()));

		this.unauthenticate();

	}

	@Test
	public void testDelete() {

		this.authenticate("auditor1");

		final Folder testFolder1 = this.folderService.create(this.auditor1);
		final Folder testFolder2 = this.folderService.create(this.auditor1);

		testFolder1.setName("Mis Foldito");
		testFolder2.setName("Mis Folder");
		testFolder1.setParentFolder(testFolder2);
		testFolder2.getChildFolders().add(testFolder1);

		final Folder testFolder1Saved = this.folderService.save(testFolder1);
		final Folder testFolder2Saved = this.folderService.save(testFolder2);

		this.folderService.delete(testFolder1Saved);
		this.folderService.delete(testFolder2Saved);

		Assert.isTrue(!this.auditor1.getFolders().contains(testFolder1Saved));
		Assert.isTrue(!this.auditor1.getFolders().contains(testFolder2Saved));

		this.unauthenticate();

	}

	@Test
	public void testCreateSystemFolders() {

		this.authenticate("auditor1");

		final Collection<Folder> testFolders = this.folderService.createSystemFolders(this.auditor1);

		Assert.notNull(testFolders);
		Assert.isTrue(testFolders.size() == 5);

		final List<String> sysFolderNames = Arrays.asList("In Box", "Out Box", "Notification Box", "Trash Box", "Spam Box");
		final Integer[] contador = {
			0, 0, 0, 0, 0
		};

		for (final Folder f : testFolders) {

			final int index = sysFolderNames.indexOf(f.getName());

			Assert.isTrue(index > -1 && index < 5);
			contador[index]++;

			Assert.notNull(f);
			Assert.isTrue(f.getIsSystem());
			Assert.notNull(f.getMessages());
			Assert.isTrue(f.getMessages().isEmpty());
			Assert.notNull(f.getActor());
			Assert.isTrue(f.getActor().equals(this.auditor1));
			Assert.isNull(f.getParentFolder());
			Assert.notNull(f.getChildFolders());
			Assert.isTrue(f.getChildFolders().isEmpty());
			Assert.isTrue(this.auditor1.getFolders().contains(f));

		}

		for (int i = 0; i < contador.length; i++)
			Assert.isTrue(contador[i] == 1);

		this.unauthenticate();

	}

	@Test
	public void testFindAll() {

		this.authenticate("auditor1");

		final Folder testFolder = this.folderService.create(this.auditor1);
		testFolder.setName("Mis Cosas");

		final Folder savedTestFolder = this.folderService.save(testFolder);

		final Collection<Folder> foundFolders = this.folderService.findAll();

		Assert.isTrue(foundFolders.contains(savedTestFolder));
		//Because 'Dua Lipa' actor has only one folder by default
		Assert.isTrue(foundFolders.size() == 2);

		this.unauthenticate();

	}

	@Test
	public void testFindOne() {

		this.authenticate("auditor1");

		final Folder testFolder = this.folderService.create(this.auditor1);
		testFolder.setName("Mis Cosas");

		final Folder savedTestFolder = this.folderService.save(testFolder);

		final Folder foundFolder = this.folderService.findOne(savedTestFolder.getId());

		Assert.isTrue(savedTestFolder.equals(foundFolder));

		this.unauthenticate();

	}

	@Test
	public void testFindAllGivenActor() {

		this.authenticate("auditor1");

		final Folder testFolder = this.folderService.create(this.auditor1);
		testFolder.setName("Mis Cosas");

		this.folderService.save(testFolder);

		final Collection<Folder> foundFolders = this.folderService.findAll();

		this.unauthenticate();

		this.authenticate("admin1");

		final Collection<Folder> foundFoldersGivenActor = this.folderService.findAllGivenActor(this.auditor1);

		Assert.notNull(foundFoldersGivenActor);
		Assert.isTrue(foundFolders.equals(foundFoldersGivenActor));

		this.unauthenticate();

	}

	@Test
	public void testFindOneGivenActor() {

		this.authenticate("auditor1");

		final Folder testFolder = this.folderService.create(this.auditor1);
		testFolder.setName("Mis Cosas");

		final Folder savedTestFolder = this.folderService.save(testFolder);

		final Folder foundFolder = this.folderService.findOne(savedTestFolder.getId());

		this.unauthenticate();

		this.authenticate("admin1");

		final Folder foundFolderGivenActor = this.folderService.findOneGivenActor(savedTestFolder.getId(), this.auditor1);

		Assert.notNull(foundFolderGivenActor);
		Assert.isTrue(foundFolder.equals(foundFolderGivenActor));

		this.unauthenticate();

	}

	@Test
	public void testFindByActorAndName() {

		this.authenticate("auditor1");

		final Folder testFolder = this.folderService.create(this.auditor1);
		testFolder.setName("Mis Cosas");

		final Folder savedTestFolder = this.folderService.save(testFolder);

		final Folder foundFolder = this.folderService.findOne(savedTestFolder.getId());

		this.unauthenticate();

		this.authenticate("admin1");

		final Folder foundFolderByActorAndName = this.folderService.findByActorAndName(this.auditor1, "Mis Cosas");

		Assert.notNull(foundFolderByActorAndName);
		Assert.isTrue(foundFolder.equals(foundFolderByActorAndName));

		this.unauthenticate();

	}

}
