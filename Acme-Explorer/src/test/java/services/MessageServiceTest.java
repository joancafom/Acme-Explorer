package services;

import java.util.ArrayList;
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
import domain.Actor;
import domain.Explorer;
import domain.Folder;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class MessageServiceTest extends AbstractTest{
	@Autowired
	private MessageService messageService;
	@Autowired
	private ExplorerService explorerService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private FolderService folderService;
	
	@Test
	public void testCreate(){
		super.authenticate("explorer1");
		UserAccount userAccount = LoginService.getPrincipal();
		Explorer explorer = this.explorerService.findByUserAccount(userAccount); 
		Message message = this.messageService.create();
		Assert.notNull(message);
		Assert.isTrue(message.getSender().equals(explorer));
	}
	
	@Test
	public void testSave(){
		super.authenticate("explorer1");
		//UserAccount userAccount = LoginService.getPrincipal();
		//Explorer explorer = this.explorerService.findByUserAccount(userAccount);
		Message message = this.messageService.create();
		message.setBody("Melodrama");
		this.messageService.save(message);
		Assert.isTrue(message.getBody().equals("Melodrama"));
		
	}
	
	@Test
	public void testDelete(){
		super.authenticate("explorer1");
		Message message = this.messageService.create();
		this.messageService.delete(message);
	}
	
	@Test
	public void testSend(){
		super.authenticate("explorer1");
		UserAccount userAccount1 = LoginService.getPrincipal();
		Explorer explorer1 = this.explorerService.findByUserAccount(userAccount1);
		super.authenticate("explorer2");
		UserAccount userAccount2 = LoginService.getPrincipal();
		Explorer explorer2 = this.explorerService.findByUserAccount(userAccount2);
		super.authenticate("explorer1");
		
		Message message = this.messageService.create();
		Folder folder1 = new Folder();
		Folder folder2 = new Folder();
		folder1.setName("Out Box");
		folder2.setName("In Box");
		explorer1.getFolders().add(folder1);
		explorer2.getFolders().add(folder2);
		
		this.messageService.send(explorer2, message);
		
		Assert.isTrue(explorer1.getSentMessages().contains(message));
		Assert.isTrue(explorer2.getReceivedMessages().contains(message));
		
	}
	
	@Test
	public void testSendNotification(){
		super.authenticate("admin1");
		Message message = this.messageService.create();
		List<Actor> actors = new ArrayList<Actor>(this.actorService.findAll());
		Actor actor = actors.get(0);
		this.messageService.sendNotification(actor, message);
		
	}
	
	@Test
	public void testChangeFolder(){
		super.authenticate("explorer1");
		UserAccount userAccount1 = LoginService.getPrincipal();
		Explorer explorer1 = this.explorerService.findByUserAccount(userAccount1);
		Folder folder = this.folderService.create(explorer1);
		Message message = this.messageService.create();
		
		this.messageService.changeMessageFolder(message, folder);
		Assert.isTrue(message.getFolder().equals(folder));
	}
	
}
