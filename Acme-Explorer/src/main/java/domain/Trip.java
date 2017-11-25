
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Trip extends DomainEntity {

	private String	ticker;
	private String	title;
	private String	description;
	private double	price;
	private Date	publicationDate;
	private Date	startingDate;
	private Date	endingDate;
	private String	requirements;
	private String	cancelationReason;


	//@Unique
	@Pattern(regexp = "(^\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])-[A-Z]{4}$)")
	public String getTicker() {
		return this.ticker;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@Min(0)
	@Digits(fraction = 2, integer = 99)
	public double getPrice() {
		return this.price;
	}

	@NotNull
	public Date getPublicationDate() {
		return this.publicationDate;
	}

	@NotNull
	public Date getStartingDate() {
		return this.startingDate;
	}

	@NotNull
	public Date getEndingDate() {
		return this.endingDate;
	}

	@NotBlank
	public String getRequirements() {
		return this.requirements;
	}

	public String getCancelationReason() {
		return this.cancelationReason;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public void setPublicationDate(final Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public void setStartingDate(final Date startingDate) {
		this.startingDate = startingDate;
	}

	public void setEndingDate(final Date endingDate) {
		this.endingDate = endingDate;
	}

	public void setRequirements(final String requirements) {
		this.requirements = requirements;
	}

	public void setCancelationReason(final String cancelationReason) {
		this.cancelationReason = cancelationReason;
	}


	//Relationships

	private Collection<Sponsorship>		sponsorships;
	private Collection<Story>			stories;
	private Collection<Note>			notes;
	private Collection<Audition>		auditions;
	private Collection<TripApplication>	tripApplications;
	private Collection<TagValue>		tagValues;
	private LegalText					legalText;
	private Collection<Stage>			stages;
	private Category					category;
	private Ranger						ranger;
	private Collection<SurvivalClass>	survivalClasses;
	private Manager						manager;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<Story> getStories() {
		return this.stories;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<Note> getNotes() {
		return this.notes;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<Audition> getAuditions() {
		return this.auditions;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<TripApplication> getTripApplications() {
		return this.tripApplications;
	}

	@NotNull
	@Valid
	@ManyToMany
	//mappedBy should be in the TagValue class!!
	public Collection<TagValue> getTagValues() {
		return this.tagValues;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	//mappedBy should be in the LegalText class!!
	public LegalText getLegalText() {
		return this.legalText;
	}

	@NotEmpty
	@Valid
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "trip")
	public Collection<Stage> getStages() {
		return this.stages;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	//mappedBy should be in the Category class!!
	public Category getCategory() {
		return this.category;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	//mappedBy should be in the Ranger class!!
	public Ranger getRanger() {
		return this.ranger;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<SurvivalClass> getSurvivalClasses() {
		return this.survivalClasses;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	// mappedBy should be in the Manager class!!
	public Manager getManager() {
		return this.manager;
	}
	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

	public void setStories(final Collection<Story> stories) {
		this.stories = stories;
	}

	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}

	public void setAuditions(final Collection<Audition> auditions) {
		this.auditions = auditions;
	}

	public void setTripApplications(final Collection<TripApplication> tripApplications) {
		this.tripApplications = tripApplications;
	}

	public void setTagValues(final Collection<TagValue> tagValues) {
		this.tagValues = tagValues;
	}

	public void setLegalText(final LegalText legalText) {
		this.legalText = legalText;
	}

	public void setStages(final Collection<Stage> stages) {
		this.stages = stages;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	public void setRanger(final Ranger ranger) {
		this.ranger = ranger;
	}

	public void setSurvivalClasses(final Collection<SurvivalClass> survivalClasses) {
		this.survivalClasses = survivalClasses;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}

}
