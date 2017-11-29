
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	private Date			sentMoment;
	private String			subject;
	private String			body;
	private PriorityLevel	priority;


	//@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getSentMoment() {
		return this.sentMoment;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	public PriorityLevel getPriority() {
		return this.priority;
	}

	public void setSentMoment(final Date sentMoment) {
		this.sentMoment = sentMoment;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public void setPriority(final PriorityLevel priority) {
		this.priority = priority;
	}


	//Relationships

	private Actor	recipient;
	private Actor	sender;
	private Folder	folder;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getRecipient() {
		return this.recipient;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getSender() {
		return this.sender;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Folder getFolder() {
		return this.folder;
	}

	public void setRecipient(final Actor recipient) {
		this.recipient = recipient;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	public void setFolder(final Folder folder) {
		this.folder = folder;
	}

}
