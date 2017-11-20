
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
public class Category extends DomainEntity {

	private String	name;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}


	//Relationships

	private Collection<Trip>		trips;
	private Category				parentCategory;
	private Collection<Category>	childCategories;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "category")
	public Collection<Trip> getTrips() {
		return this.trips;
	}

	@Valid
	@ManyToOne(optional = true)
	public Category getParentCategory() {
		return this.parentCategory;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "parentCategory")
	public Collection<Category> getChildCategories() {
		return this.childCategories;
	}

	public void setTrips(final Collection<Trip> trips) {
		this.trips = trips;
	}

	public void setParentCategory(final Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public void setChildCategories(final Collection<Category> childCategories) {
		this.childCategories = childCategories;
	}

}
