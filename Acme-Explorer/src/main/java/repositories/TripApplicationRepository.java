
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.TripApplication;

@Repository
public interface TripApplicationRepository extends JpaRepository<TripApplication, Integer> {

	@Query("select sc.trip.tripApplications from SurvivalClass sc where sc.manager.id=?1")
	Collection<TripApplication> findTripApplicationsManagedByManager(int managerId);

	@Query("select t from TripApplication t where t.explorer.id = ?1 group by t.status")
	Collection<TripApplication> findTripApplicationsByExplorer(int exploredId);

	@Query("select t from TripApplication t where t.status='ACCEPTED' and t.explorer.id=?1")
	Collection<TripApplication> findAcceptedTripApplicationsByExplorer(int id);

}
