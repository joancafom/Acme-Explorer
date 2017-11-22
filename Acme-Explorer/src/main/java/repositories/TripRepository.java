
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

	@Query("select t from Trip t where t.ticker like %?1% or t.title like %?1% or t.description like %?1%")
	Collection<Trip> findByKeyWord(String keyWord);

	@Query("select t from Trip t where t.category.id = ?1")
	Collection<Trip> findByCategoryId(int categoryId);

	@Query("select t from Trip t where t.publicationDate > CURRENT_DATE")
	Collection<Trip> findAllNotPublished();

	@Query("select s.trip from SurvivalClass s where s.manager.id = ?1")
	Collection<Trip> findAllManagedBy(int managerId);

	@Query("select t from Trip t where t.publicationDate < CURRENT_DATE and t.startingDate > CURRENT_DATE")
	Collection<Trip> findPublishedButNotStarted();

	@Query("select t from Trip t where t.startingDate > CURRENT_DATE")
	Collection<Trip> findAllNotStarted();

	// REVISAR

	@Query("select t from Trip t where t.price > ?1 and t.price < ?2 and t.startingDate > ?3 and t.endingDate < ?4 and (t.ticker like %?5% or t.title like %?5% or t.description like %?5%)")
	Collection<Trip> findFilterFinder(double minPrice, double maxPrice, Date minDate, Date maxDate, String keyWord);

	@Query("select t.trip from TripApplication t where t.explorer.id = ?1 and t.status = 'ACCEPTED'")
	Collection<Trip> findExplorerAcceptedTrips(int explorerId);

	@Query("select t.trip from TripApplication t where t.explorer.id = ?1 and t.status = 'ACCEPTED' and t.trip.endingDate < CURRENT_DATE")
	Collection<Trip> findExplorerAcceptedAndOverTrips(int explorerId);

}
