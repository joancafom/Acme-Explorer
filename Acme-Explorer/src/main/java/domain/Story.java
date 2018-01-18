
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Story extends DomainEntity {

	private String				title;
	private String				text;
	private Collection<String>	attachments;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	@ElementCollection
	@NotNull
	public Collection<String> getAttachments() {
		return this.attachments;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}


	//Relationships

	private Explorer	explorer;
	private Trip		trip;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	//mappedBy should be in the Explorer class!!
	public Explorer getExplorer() {
		return this.explorer;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	//mappedBy should be in the Trip class!!
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
