package services;

import java.util.ArrayList;
import java.util.Date;
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
import domain.Explorer;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;
import domain.TripApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class SurvivalClassServiceTest extends AbstractTest{
	
	@Autowired
	private SurvivalClassService survivalClassService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private ExplorerService explorerService;
	@Autowired
	private TripApplicationService tripApplicationService;
	
	
	@Test
	public void testCreate(){
		super.authenticate("manager1");
		UserAccount userAccount = LoginService.getPrincipal();
		Manager manager = this.managerService.findByUserAccount(userAccount);
		List<Trip> trips = new ArrayList<Trip>(manager.getTrips());
		Trip trip = trips.get(0);
		
		SurvivalClass survivalClass = this.survivalClassService.create(trip);
		Assert.notNull(survivalClass);
		Assert.isTrue(survivalClass.getManager().equals(manager));
		Assert.isTrue(survivalClass.getTrip().equals(trip));
		
	}
	
	@Test
	public void testSave(){
		super.authenticate("manager1");
		UserAccount userAccount = LoginService.getPrincipal();
		Manager manager = this.managerService.findByUserAccount(userAccount);
		List<Trip> trips = new ArrayList<Trip>(manager.getTrips());
		Trip trip = trips.get(0);
		SurvivalClass survivalClass = this.survivalClassService.create(trip);
		survivalClass.setTitle("Lust For Life");
		
		this.survivalClassService.save(survivalClass);
		Assert.isTrue(manager.getSurvivalClasses().contains(survivalClass));
		Assert.isTrue(trip.getSurvivalClasses().contains(survivalClass));
		Assert.isTrue(survivalClass.getTitle().equals("Lust For Life"));
		
	}
	
	@Test
	public void testDelete(){
		super.authenticate("manager1");
		UserAccount userAccount = LoginService.getPrincipal();
		Manager manager = this.managerService.findByUserAccount(userAccount);
		List<Trip> trips = new ArrayList<Trip>(manager.getTrips());
		Trip trip = trips.get(0);
		SurvivalClass survivalClass = this.survivalClassService.create(trip);
		this.survivalClassService.delete(survivalClass);
	}
	
	@Test
	public void testFindByCurrentManager(){
		super.authenticate("manager1");
		UserAccount userAccount = LoginService.getPrincipal();
		Manager manager = this.managerService.findByUserAccount(userAccount);
		List<SurvivalClass> survivalClasses = new ArrayList<SurvivalClass>(manager.getSurvivalClasses());
		Assert.isTrue(survivalClasses.containsAll(this.survivalClassService.findByCurrentManager())
				&& survivalClasses.size()==this.survivalClassService.findByCurrentManager().size());
	}
	
	@Test
	public void testFindOneByCurrentManager(){
		super.authenticate("manager1");
		UserAccount userAccount = LoginService.getPrincipal();
		Manager manager = this.managerService.findByUserAccount(userAccount);
		List<SurvivalClass> survivalClasses = new ArrayList<SurvivalClass>(manager.getSurvivalClasses());
		SurvivalClass survivalClass = survivalClasses.get(0);
		
		Assert.isTrue(manager.getSurvivalClasses().contains(this.survivalClassService.findOneCurrentManager(survivalClass.getId())));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testEnrroll(){
		super.authenticate("explorer2");
		UserAccount userAccount2 = LoginService.getPrincipal();
		Explorer explorer = this.explorerService.findByUserAccount(userAccount2);
		List<TripApplication> tripApplications = new ArrayList<TripApplication>(this.tripApplicationService.findAcceptedByCurrentExplorer());
		List<SurvivalClass> survivalClasses = new ArrayList<SurvivalClass>(tripApplications.get(0).getTrip().getSurvivalClasses());
		SurvivalClass survivalClass = survivalClasses.get(0);
		survivalClass.setMoment(new Date(2017, 12, 02));
		this.survivalClassService.enroll(survivalClass);
		
		Assert.isTrue(survivalClass.getExplorers().contains(explorer));
		Assert.isTrue(explorer.getSurvivalClasses().contains(survivalClass));
	}

}
