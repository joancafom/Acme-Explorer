
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String	keyword;
	private Double	minRange;
	private Double	maxRange;
	private Date	minDate;
	private Date	maxDate;
	private Integer	cacheTime;


	public String getKeyword() {
		return this.keyword;
	}

	public Double getMinRange() {
		return this.minRange;
	}

	public Double getMaxRange() {
		return this.maxRange;
	}

	public Date getMinDate() {
		return this.minDate;
	}

	public Date getMaxDate() {
		return this.maxDate;
	}

	@Min(1)
	@Max(24)
	public Integer getCacheTime() {
		return this.cacheTime;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	public void setMinRange(final Double minRange) {
		this.minRange = minRange;
	}

	public void setMaxRange(final Double maxRange) {
		this.maxRange = maxRange;
	}

	public void setMinDate(final Date minDate) {
		this.minDate = minDate;
	}

	public void setMaxDate(final Date maxDate) {
		this.maxDate = maxDate;
	}

	public void setCacheTime(final Integer cacheTime) {
		this.cacheTime = cacheTime;
	}

}
