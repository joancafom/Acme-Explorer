
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfiguration extends DomainEntity {

	private double				VAT;
	private String				banner;
	private String				welcomeMessageEN;
	private String				welcomeMessageES;
	private Collection<String>	spamWords;
	private String				countryCode;


	@Digits(fraction = 2, integer = 1)
	@Min(0)
	@Max(1)
	public double getVAT() {
		return this.VAT;
	}

	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	@NotBlank
	public String getWelcomeMessageEN() {
		return this.welcomeMessageEN;
	}

	@NotBlank
	public String getWelcomeMessageES() {
		return this.welcomeMessageES;
	}

	@NotEmpty
	//@NotBlank
	@ElementCollection
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	@NotBlank
	@Pattern(regexp = "^\\+[0-9]{1,3}$")
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setVAT(final double vAT) {
		this.VAT = vAT;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setWelcomeMessageEN(final String welcomeMessageEN) {
		this.welcomeMessageEN = welcomeMessageEN;
	}

	public void setWelcomeMessageES(final String welcomeMessageES) {
		this.welcomeMessageES = welcomeMessageES;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}
}
