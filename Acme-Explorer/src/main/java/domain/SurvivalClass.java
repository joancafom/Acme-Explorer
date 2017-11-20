
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class SurvivalClass extends DomainEntity {

	private String		title;
	private String		description;
	private Date		moment;
	private Location	location;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	@Valid
	@NotNull
	public Location getLocation() {
		return this.location;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setLocation(final Location location) {
		this.location = location;
	}


	//Relationships

	private Manager					manager;
	private Trip					trip;
	private Collection<Explorer>	explorers;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Trip getTrip() {
		return this.trip;
	}

	@NotNull
	@Valid
	@ManyToMany()
	// mappedBy in Explorer class!!
	public Collection<Explorer> getExplorers() {
		return this.explorers;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

	public void setExplorers(final Collection<Explorer> explorers) {
		this.explorers = explorers;
	}
}
