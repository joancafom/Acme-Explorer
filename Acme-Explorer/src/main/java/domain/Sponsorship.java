
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
public class Sponsorship extends DomainEntity {

	private String		bannerUrl;
	private String		infoPageLink;
	private CreditCard	creditCard;


	@NotBlank
	@URL
	public String getBannerUrl() {
		return this.bannerUrl;
	}

	@NotBlank
	@URL
	public String getInfoPageLink() {
		return this.infoPageLink;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setBannerUrl(final String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	public void setInfoPageLink(final String infoPageLink) {
		this.infoPageLink = infoPageLink;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}


	//Relationships

	private Trip	trip;
	private Sponsor	sponsor;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	//mappedBy should be in the Trip class!!
	public Trip getTrip() {
		return this.trip;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	//mappedBy should be in the Sponsor class!!
	public Sponsor getSponsor() {
		return this.sponsor;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

	public void setSponsor(final Sponsor sponsor) {
		this.sponsor = sponsor;
	}

}
