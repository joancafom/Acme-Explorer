
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String	keyword;
	private Double	minRange;
	private Double	maxRange;
	private Date	minDate;
	private Date	maxDate;


	//private Collection<Trip>	cache;

	public String getKeyword() {
		return this.keyword;
	}

	public Double getMinRange() {
		return this.minRange;
	}

	public Double getMaxRange() {
		return this.maxRange;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMinDate() {
		return this.minDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMaxDate() {
		return this.maxDate;
	}

	/*
	 * public Collection<Trip> getCache() {
	 * return this.cache;
	 * }
	 */

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

	/*
	 * public void setCache(final Collection<Trip> cache) {
	 * this.cache = cache;
	 * }
	 */

}
