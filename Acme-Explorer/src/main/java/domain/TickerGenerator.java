
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class TickerGenerator extends DomainEntity {

	private Collection<String>	tickers;


	@NotEmpty
	@ElementCollection
	public Collection<String> getTickers() {
		return this.tickers;
	}

	public void setTickers(final Collection<String> tickers) {
		this.tickers = tickers;
	}

}
