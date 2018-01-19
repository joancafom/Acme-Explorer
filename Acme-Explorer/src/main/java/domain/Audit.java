
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Audit extends DomainEntity {

	private Date				moment;
	private String				title;
	private String				description;
	private Collection<String>	attachments;
	private boolean				isFinal;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@ElementCollection
	@NotNull
	public Collection<String> getAttachments() {
		return this.attachments;
	}

	public boolean getIsFinal() {
		return this.isFinal;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}

	public void setIsFinal(final boolean isFinal) {
		this.isFinal = isFinal;
	}


	//Relationships

	private Auditor	auditor;
	private Trip	trip;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Auditor getAuditor() {
		return this.auditor;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Trip getTrip() {
		return this.trip;
	}

	public void setAuditor(final Auditor auditor) {
		this.auditor = auditor;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}
}
