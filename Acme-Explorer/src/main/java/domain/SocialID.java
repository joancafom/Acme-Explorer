
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialID extends DomainEntity {

	private String	nick;
	private String	nameSocialNetwork;
	private String	link;
	private String	photo;


	@NotBlank
	public String getNick() {
		return this.nick;
	}

	@NotBlank
	public String getNameSocialNetwork() {
		return this.nameSocialNetwork;
	}

	@NotBlank
	@URL
	public String getLink() {
		return this.link;
	}

	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setNick(final String nick) {
		this.nick = nick;
	}

	public void setNameSocialNetwork(final String nameSocialNetwork) {
		this.nameSocialNetwork = nameSocialNetwork;
	}

	public void setLink(final String link) {
		this.link = link;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}


	//Relationships

	private Actor	actor;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	//mappedBy should be in the Actor class!!
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
