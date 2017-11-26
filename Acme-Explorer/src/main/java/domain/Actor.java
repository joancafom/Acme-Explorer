
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	private String	name;
	private String	surname;
	private String	email;
	private String	phoneNumber;
	private String	address;
	private boolean	isSuspicious;
	private boolean	isBanned;


	public boolean getIsSuspicious() {
		return this.isSuspicious;
	}

	public boolean getIsBanned() {
		return this.isBanned;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
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

	public String getAddress() {
		return this.address;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public void setIsSuspicious(final boolean isSuspicious) {
		this.isSuspicious = isSuspicious;
	}

	public void setIsBanned(final boolean isBanned) {
		this.isBanned = isBanned;
	}


	//Relationships

	private Collection<SocialID>	socialIDs;
	private Collection<Folder>		folders;
	private Collection<Message>		sentMessages;
	private Collection<Message>		receivedMessages;
	private UserAccount				userAccount;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "actor")
	public Collection<SocialID> getSocialIDs() {
		return this.socialIDs;
	}

	@NotEmpty
	@Valid
	@OneToMany(mappedBy = "actor")
	public Collection<Folder> getFolders() {
		return this.folders;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "sender")
	public Collection<Message> getSentMessages() {
		return this.sentMessages;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "recipient")
	public Collection<Message> getReceivedMessages() {
		return this.receivedMessages;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setSocialIDs(final Collection<SocialID> socialIDs) {
		this.socialIDs = socialIDs;
	}

	public void setFolders(final Collection<Folder> folders) {
		this.folders = folders;
	}

	public void setSentMessages(final Collection<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}

	public void setReceivedMessages(final Collection<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
