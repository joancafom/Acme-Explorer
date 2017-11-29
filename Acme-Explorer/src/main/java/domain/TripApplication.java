
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class TripApplication extends DomainEntity {

	private Date				moment;
	private ApplicationStatus	status;
	private Collection<String>	comments;
	private String				rejectionReason;
	private CreditCard			creditCard;


	@NotNull
	////@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	public ApplicationStatus getStatus() {
		return this.status;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getComments() {
		return this.comments;
	}

	public String getRejectionReason() {
		return this.rejectionReason;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setStatus(final ApplicationStatus status) {
		this.status = status;
	}

	public void setComments(final Collection<String> comments) {
		this.comments = comments;
	}

	public void setRejectionReason(final String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}


	//Relationships

	private Explorer	explorer;
	private Trip		trip;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Explorer getExplorer() {
		return this.explorer;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Trip getTrip() {
		return this.trip;
	}

	public void setExplorer(final Explorer explorer) {
		this.explorer = explorer;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

}
