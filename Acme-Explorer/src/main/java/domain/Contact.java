
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

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
