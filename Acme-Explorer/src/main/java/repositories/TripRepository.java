
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Query("select t from Trip t where t.publicationDate > CURRENT_TIMESTAMP")
	Collection<Trip> findAllNotPublished();

	@Query("select m.trips from Manager m where m.id = ?1")
	Collection<Trip> findAllManagedBy(int managerId);

	@Query("select t from Trip t where t.publicationDate < CURRENT_TIMESTAMP and t.startingDate > CURRENT_TIMESTAMP")
	Collection<Trip> findPublishedButNotStarted();

	@Query("select t from Trip t where t.startingDate > CURRENT_TIMESTAMP")
	Collection<Trip> findAllNotStarted();

	@Query("select a.trip from Audit a where a.auditor.id = ?1")
	Collection<Trip> findAuditorAuditedTrips(int auditorId);

	// REVISAR

	//DONE: Limit the Query
	@Query(value = "select t from Trip t where t.price > ?1 and t.price < ?2 and t.startingDate > ?3 and t.endingDate < ?4 and (t.ticker like %?5% or t.title like %?5% or t.description like %?5%) and t.publicationDate < CURRENT_TIMESTAMP")
	Page<Trip> findByFinderAttributesPublished(Pageable pageable, double minPrice, double maxPrice, Date minDate, Date maxDate, String keyWord);

	@Query(value = "select t from Trip t where t.price > ?1 and t.price < ?2 and (t.ticker like %?3% or t.title like %?3% or t.description like %?3%) and t.publicationDate < CURRENT_TIMESTAMP")
	Page<Trip> findByKeyWordAndRangePublished(Pageable pageable, double minPrice, double maxPrice, String keyWord);

	@Query(value = "select t from Trip t where t.startingDate > ?1 and t.endingDate < ?2 and (t.ticker like %?3% or t.title like %?3% or t.description like %?3%) and t.publicationDate < CURRENT_TIMESTAMP")
	Page<Trip> findByKeyWordAndDatePublished(Pageable pageable, Date minDate, Date maxDate, String keyWord);

	@Query("select t.trip from TripApplication t where t.explorer.id = ?1 and t.status = 'ACCEPTED'")
	Collection<Trip> findExplorerAcceptedTrips(int explorerId);

	@Query("select t.trip from TripApplication t where t.explorer.id = ?1 and t.status = 'ACCEPTED' and t.trip.endingDate < CURRENT_TIMESTAMP")
	Collection<Trip> findExplorerAcceptedAndOverTrips(int explorerId);

	@Query("select t from Trip t where t.publicationDate < CURRENT_TIMESTAMP")
	Collection<Trip> findAllPublished2();

	@Query(value = "select t from Trip t where t.publicationDate < CURRENT_TIMESTAMP")
	Page<Trip> findAllPublished(Pageable pageable);

	@Query("select t from Trip t where t.category.id = ?1 and t.publicationDate < CURRENT_TIMESTAMP")
	Collection<Trip> findByCategoryIdPublished(int categoryId);

	@Query("select t from Trip t where (t.ticker like %?1% or t.title like %?1% or t.description like %?1%) and t.publicationDate < CURRENT_TIMESTAMP")
	Collection<Trip> findByKeyWordPublished2(String keyWord);

	@Query(value = "select t from Trip t where (t.ticker like %?1% or t.title like %?1% or t.description like %?1%) and t.publicationDate < CURRENT_TIMESTAMP")
	Page<Trip> findByKeyWordPublished(Pageable pageable, String keyWord);

}
