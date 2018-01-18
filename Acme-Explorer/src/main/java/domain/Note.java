
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	private Date	writtenMoment;
	private String	remark;
	private String	reply;
	private Date	replyMoment;


	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getWrittenMoment() {
		return this.writtenMoment;
	}

	@NotBlank
	public String getRemark() {
		return this.remark;
	}

	public String getReply() {
		return this.reply;
	}

	//@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getReplyMoment() {
		return this.replyMoment;
	}

	public void setWrittenMoment(final Date writtenMoment) {
		this.writtenMoment = writtenMoment;
	}

	public void setRemark(final String remark) {
		this.remark = remark;
	}

	public void setReply(final String reply) {
		this.reply = reply;
	}

	public void setReplyMoment(final Date replyMoment) {
		this.replyMoment = replyMoment;
	}


	//Relationships

	private Auditor	auditor;
	private Trip	trip;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	//mappedBy should be in the Auditor class!!
	public Auditor getAuditor() {
		return this.auditor;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	//mappedBy should be in the Trip class!!
	public Trip getTrip() {
		return this.trip;
	}

	public void setAuditor(final Auditor auditor) {
		this.auditor = auditor;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

}
