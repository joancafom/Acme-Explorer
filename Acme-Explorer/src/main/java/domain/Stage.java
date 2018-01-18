
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Stage extends DomainEntity {

	private String	title;
	private String	description;
	private double	price;
	private int		number;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@Min(0)
	@Digits(fraction = 2, integer = 99)
	public double getPrice() {
		return this.price;
	}

	public int getNumber() {
		return this.number;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public void setNumber(final int number) {
		this.number = number;
	}


	//Relationships

	private Trip	trip;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	//mappedBy should be in the Trip class!!
	public Trip getTrip() {
		return this.trip;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

}
