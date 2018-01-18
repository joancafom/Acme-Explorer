
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity {

	private boolean	isSystem;
	private String	name;


	public boolean getIsSystem() {
		return this.isSystem;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setIsSystem(final boolean isSystem) {
		this.isSystem = isSystem;
	}

	public void setName(final String name) {
		this.name = name;
	}


	//Relationships

	private Collection<Message>	messages;
	private Folder				parentFolder;
	private Collection<Folder>	childFolders;
	private Actor				actor;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "folder")
	public Collection<Message> getMessages() {
		return this.messages;
	}

	@Valid
	@ManyToOne(optional = true)
	public Folder getParentFolder() {
		return this.parentFolder;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "parentFolder")
	public Collection<Folder> getChildFolders() {
		return this.childFolders;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

	public void setParentFolder(final Folder parentFolder) {
		this.parentFolder = parentFolder;
	}

	public void setChildFolders(final Collection<Folder> childFolders) {
		this.childFolders = childFolders;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
