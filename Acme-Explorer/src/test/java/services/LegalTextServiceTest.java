package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.LegalTextRepository;
import utilities.AbstractTest;
import domain.LegalText;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class LegalTextServiceTest extends AbstractTest{

	@Autowired
	private LegalTextService legalTextService;
	@Autowired
	private LegalTextRepository legalTextRepository;

	@Test
	public void testCreate(){
		LegalText legalText = this.legalTextService.create();
		long millis;
		Date registrationMoment;
		millis = System.currentTimeMillis() - 1000;
		registrationMoment = new Date(millis);
		Assert.isTrue(legalText.getRegistrationMoment().equals(registrationMoment));
		Assert.isTrue(legalText.getTrips().isEmpty());
		Assert.notNull(legalText);
	}
	
	@Test
	public void testFindAll(){
		Collection<LegalText> legalTexts = this.legalTextRepository.findAll();
		Collection<LegalText> legalTexts2 = this.legalTextService.findAll();
		Assert.isTrue(legalTexts.containsAll(legalTexts2) &&
				legalTexts.size()==legalTexts2.size());
		
	}
	
	@Test
	public void testFindOne(){
		List<LegalText> legalTexts = this.legalTextRepository.findAll();
		LegalText legalText = legalTexts.get(0);
		
		Assert.isTrue(legalText.equals(this.legalTextService.findOne(legalText.getId())));
	}
	
	@Test
	public void testSave(){
		List<LegalText> legalTexts = this.legalTextRepository.findAll();
		LegalText legalText = legalTexts.get(0);
		legalText.setIsFinal(false);
		legalText.setBody("Havana");
		legalText.setTrips(new ArrayList<Trip>());
		
		this.legalTextService.save(legalText);
		Assert.isTrue(legalText.getBody().equals("Havana"));
	}
	
	@Test
	public void testDelete(){
		List<LegalText> legalTexts = this.legalTextRepository.findAll();
		LegalText legalText = legalTexts.get(0);
		legalText.setIsFinal(false);
		this.legalTextService.delete(legalText);
	}
	
	
}
