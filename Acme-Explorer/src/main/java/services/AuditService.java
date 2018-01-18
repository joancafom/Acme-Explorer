
package services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import security.LoginService;
import security.UserAccount;
import domain.Audit;
import domain.Auditor;
import domain.SystemConfiguration;
import domain.Trip;

@Service
@Transactional
public class AuditService {

	/* Repositories */
	@Autowired
	private AuditRepository				auditRepository;

	/* Services */
	@Autowired
	private AuditorService				auditorService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	/* CRUD */
	public Audit create() {
		long millis;
		final Date moment;

		final UserAccount userAccount = LoginService.getPrincipal();

		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);

		final Audit audit = new Audit();
		audit.setAuditor(auditor);

		final Collection<String> attachments = new ArrayList<String>();
		audit.setAttachments(attachments);

		millis = System.currentTimeMillis() - 1000;
		moment = new Date(millis);

		audit.setMoment(moment);
		audit.setIsFinal(false);

		return audit;
	}

	public Collection<Audit> findByCurrentAuditor() {

		final UserAccount userAccount = LoginService.getPrincipal();

		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);

		Assert.notNull(auditor);

		final Collection<Audit> audits = this.auditRepository.findAuditsManagedByAuditor(auditor.getId());

		return audits;

	}

	public Audit save(final Audit audit) {
		final UserAccount userAccount = LoginService.getPrincipal();

		final Auditor auditor = this.auditorService.findByUserAccount(userAccount);
		Assert.isTrue(audit.getAuditor().equals(auditor));

		if (audit.getId() != 0)
			Assert.isTrue(!this.findOne(audit.getId()).getIsFinal());
		else
			Assert.isTrue(this.findByAuditorAndTrip(auditor, audit.getTrip()) == null);

		if (!audit.getAttachments().isEmpty())
			for (final String s : audit.getAttachments())
				try {
					@SuppressWarnings("unused")
					final URL url = new java.net.URL(s);
				} catch (final MalformedURLException e) {
					throw new IllegalArgumentException();
				}

		final Boolean isSuspicious;
		isSuspicious = this.decideSuspiciousness(audit.getTitle() + " " + audit.getDescription());

		if (isSuspicious)
			auditor.setIsSuspicious(isSuspicious);

		return this.auditRepository.save(audit);

	}
	public void delete(final Audit audit) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(!audit.getIsFinal());

		Assert.isTrue(audit.getAuditor().getUserAccount().equals(userAccount));

		this.auditRepository.delete(audit);
	}

	public Collection<Audit> findAll() {
		return this.auditRepository.findAll();
	}

	public Audit findOne(final int id) {
		return this.auditRepository.findOne(id);
	}

	//Other Business Methods

	public Collection<Audit> findByTrip(final Trip t) {
		Assert.notNull(t);
		return this.auditRepository.findByTripId(t.getId());
	}

	public Audit findByAuditorAndTrip(final Auditor auditor, final Trip trip) {
		Assert.notNull(auditor);
		Assert.notNull(trip);

		return this.auditRepository.findByAuditorIdAndTripId(auditor.getId(), trip.getId());
	}
	private Boolean decideSuspiciousness(final String testString) {
		final SystemConfiguration sysConfig = this.systemConfigurationService.getCurrentSystemConfiguration();
		Assert.notNull(sysConfig);

		Boolean res = false;

		for (final String spamWord : sysConfig.getSpamWords())
			if (testString.toLowerCase().contains(spamWord)) {
				res = true;
				break;
			}

		return res;
	}
}
