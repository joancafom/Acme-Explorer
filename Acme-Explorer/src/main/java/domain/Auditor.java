
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Auditor extends Actor {

	//Relationships

	private Collection<Note>		notes;
	private Collection<Audition>	auditions;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "auditor")
	public Collection<Note> getNotes() {
		return this.notes;
	}

	@NotNull
	@Valid
	@ManyToMany
	//mappedBy is in the Audition class!
	public Collection<Audition> getAuditions() {
		return this.auditions;
	}

	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}

	public void setAuditions(final Collection<Audition> auditions) {
		this.auditions = auditions;
	}

}
