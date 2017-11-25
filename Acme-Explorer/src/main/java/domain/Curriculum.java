
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

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	private String	ticker;


	@Pattern(regexp = "(^\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])-[A-Z]{4}$)")
	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}


	//Relationships

	private Ranger							ranger;
	private Collection<EducationRecord>		educationRecords;
	private PersonalRecord					personalRecord;
	private Collection<ProfessionalRecord>	professionalRecords;
	private Collection<EndorserRecord>		endorserRecords;
	private Collection<MiscellaneousRecord>	miscellaneousRecords;


	@NotNull
	@Valid
	@OneToOne(optional = false)
	//mappedBy should be in the Ranger class!!
	public Ranger getRanger() {
		return this.ranger;
	}

	@NotNull
	@Valid
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "curriculum")
	public Collection<EducationRecord> getEducationRecords() {
		return this.educationRecords;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false, mappedBy = "curriculum")
	public PersonalRecord getPersonalRecord() {
		return this.personalRecord;
	}

	@NotNull
	@Valid
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "curriculum")
	public Collection<ProfessionalRecord> getProfessionalRecords() {
		return this.professionalRecords;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "curriculum")
	public Collection<EndorserRecord> getEndorserRecords() {
		return this.endorserRecords;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "curriculum")
	public Collection<MiscellaneousRecord> getMiscellaneousRecords() {
		return this.miscellaneousRecords;
	}

	public void setRanger(final Ranger ranger) {
		this.ranger = ranger;
	}

	public void setEducationRecords(final Collection<EducationRecord> educationRecords) {
		this.educationRecords = educationRecords;
	}

	public void setPersonalRecord(final PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}

	public void setProfessionalRecords(final Collection<ProfessionalRecord> professionalRecords) {
		this.professionalRecords = professionalRecords;
	}

	public void setEndorserRecords(final Collection<EndorserRecord> endorserRecords) {
		this.endorserRecords = endorserRecords;
	}

	public void setMiscellaneousRecords(final Collection<MiscellaneousRecord> miscellaneousRecords) {
		this.miscellaneousRecords = miscellaneousRecords;
	}

}
