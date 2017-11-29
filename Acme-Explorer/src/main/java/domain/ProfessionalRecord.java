
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
public class ProfessionalRecord extends DomainEntity {

	private String				companyName;
	private Date				startingDate;
	private Date				endingDate;
	private String				role;
	private String				attachment;
	private Collection<String>	comments;


	@NotBlank
	public String getCompanyName() {
		return this.companyName;
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
	public String getRole() {
		return this.role;
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

	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}

	public void setStartingDate(final Date startingDate) {
		this.startingDate = startingDate;
	}

	public void setEndingDate(final Date endingDate) {
		this.endingDate = endingDate;
	}

	public void setRole(final String role) {
		this.role = role;
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
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

}
