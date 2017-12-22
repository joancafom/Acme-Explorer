
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Auditor extends Actor {

	//Relationships

	private Collection<Note>	notes;
	private Collection<Audit>	audits;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "auditor")
	public Collection<Note> getNotes() {
		return this.notes;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "auditor")
	//mappedBy is in the Audit class!
	public Collection<Audit> getAudits() {
		return this.audits;
	}

	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}

	public void setAudits(final Collection<Audit> audits) {
		this.audits = audits;
	}

}
