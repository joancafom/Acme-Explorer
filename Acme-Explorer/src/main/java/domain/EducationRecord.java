
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

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class EducationRecord extends DomainEntity {

	private String				titleOfDiploma;
	private Date				startingDate;
	private Date				endingDate;
	private String				institution;
	private String				attachment;
	private Collection<String>	comments;


	@NotBlank
	public String getTitleOfDiploma() {
		return this.titleOfDiploma;
	}

	//@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartingDate() {
		return this.startingDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndingDate() {
		return this.endingDate;
	}

	@NotBlank
	public String getInstitution() {
		return this.institution;
	}

	@URL
	public String getAttachment() {
		return this.attachment;
	}

	@ElementCollection
	@NotNull
	public Collection<String> getComments() {
		return this.comments;
	}

	public void setTitleOfDiploma(final String titleOfDiploma) {
		this.titleOfDiploma = titleOfDiploma;
	}

	public void setStartingDate(final Date startingDate) {
		this.startingDate = startingDate;
	}

	public void setEndingDate(final Date endingDate) {
		this.endingDate = endingDate;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	public void setAttachment(final String attachment) {
		this.attachment = attachment;
	}

	public void setComments(final Collection<String> comments) {
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
