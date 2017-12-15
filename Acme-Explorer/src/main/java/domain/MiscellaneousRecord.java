
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class MiscellaneousRecord extends DomainEntity {

	private String	title;
	private String	attachment;
	private String	comments;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@URL
	public String getAttachment() {
		return this.attachment;
	}

	public String getComments() {
		return this.comments;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setAttachment(final String attachment) {
		this.attachment = attachment;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}


	//Relationships

	private Curriculum	curriculum;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	//mappedBy should be in the Curriculum class!!
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

}
