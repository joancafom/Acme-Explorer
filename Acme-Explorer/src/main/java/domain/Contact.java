
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Contact {

	private String	name;
	private String	email;
	private String	phoneNumber;


	@NotBlank
	public String getName() {
		return this.name;
	}

	@Email
	public String getEmail() {
		return this.email;
	}

	@Pattern(regexp = "^(\\+[0-9]{1,3} \\([0-9]{1,3}\\) [0-9]{4,}|\\+[0-9]{1,3} [0-9]{4,}|[0-9]{4,}|.*)$")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
