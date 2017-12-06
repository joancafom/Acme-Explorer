
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class EndorserRecord extends DomainEntity {

	private String				fullName;
	private String				email;
	private String				phoneNumber;
	private String				linkedInProfile;
	private Collection<String>	comments;


	@NotBlank
	public String getFullName() {
		return this.fullName;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	@Pattern(regexp = "^(\\+[0-9]{1,3} \\([0-9]{1,3}\\) [0-9]{4,}|\\+[0-9]{1,3} [0-9]{4,}|[0-9]{4,}|.*)$")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	@NotBlank
	@URL
	public String getLinkedInProfile() {
		return this.linkedInProfile;
	}

	@ElementCollection
	@NotNull
	public Collection<String> getComments() {
		return this.comments;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setLinkedInProfile(final String linkedInProfile) {
		this.linkedInProfile = linkedInProfile;
	}

	public void setComments(final Collection<String> comments) {
		this.comments = comments;
	}


	//Relationships

	private Curriculum	curriculum;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

}