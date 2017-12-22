
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Explorer extends Actor {

	private Collection<Contact>	emergencyContacts;


	@Valid
	@NotNull
	@ElementCollection
	public Collection<Contact> getEmergencyContacts() {
		return this.emergencyContacts;
	}

	public void setEmergencyContacts(final Collection<Contact> emergencyContacts) {
		this.emergencyContacts = emergencyContacts;
	}


	//Relationships

	private Collection<TripApplication>	tripApplications;
	private Collection<Story>			stories;
	private Finder						finder;
	private Collection<SurvivalClass>	survivalClasses;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "explorer")
	public Collection<TripApplication> getTripApplications() {
		return this.tripApplications;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "explorer")
	public Collection<Story> getStories() {
		return this.stories;
	}

	@Valid
	@OneToOne(optional = true)
	public Finder getFinder() {
		return this.finder;
	}

	@NotNull
	@Valid
	@ManyToMany(mappedBy = "explorers")
	public Collection<SurvivalClass> getSurvivalClasses() {
		return this.survivalClasses;
	}

	public void setTripApplications(final Collection<TripApplication> tripApplications) {
		this.tripApplications = tripApplications;
	}

	public void setStories(final Collection<Story> stories) {
		this.stories = stories;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	public void setSurvivalClasses(final Collection<SurvivalClass> survivalClasses) {
		this.survivalClasses = survivalClasses;
	}

}
