
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Location {

	private String	name;
	private double	coordinateX;
	private double	coordinateY;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public double getCoordinateX() {
		return this.coordinateX;
	}

	public double getCoordinateY() {
		return this.coordinateY;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setCoordinateX(final double coordinateX) {
		this.coordinateX = coordinateX;
	}

	public void setCoordinateY(final double coordinateY) {
		this.coordinateY = coordinateY;
	}
}
