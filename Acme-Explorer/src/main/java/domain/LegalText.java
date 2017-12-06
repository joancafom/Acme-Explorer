
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class LegalText extends DomainEntity {

	private String	title;
	private String	body;
	private String	numberOfApplicableLaws;
	private Date	registrationMoment;
	private boolean	isFinal;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	@NotBlank
	public String getNumberOfApplicableLaws() {
		return this.numberOfApplicableLaws;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getRegistrationMoment() {
		return this.registrationMoment;
	}

	public boolean getIsFinal() {
		return this.isFinal;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public void setNumberOfApplicableLaws(final String numberOfApplicableLaws) {
		this.numberOfApplicableLaws = numberOfApplicableLaws;
	}

	public void setRegistrationMoment(final Date registrationMoment) {
		this.registrationMoment = registrationMoment;
	}

	public void setIsFinal(final boolean isFinal) {
		this.isFinal = isFinal;
	}


	//Relationships

	private Collection<Trip>	trips;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "legalText")
	public Collection<Trip> getTrips() {
		return this.trips;
	}

	public void setTrips(final Collection<Trip> trips) {
		this.trips = trips;
	}

}
