
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audition;

@Repository
public interface AuditionRepository extends JpaRepository<Audition, Integer> {

	@Query("select a.auditions from Auditor a where a.id=?1")
	Collection<Audition> findAuditionsManagedByAuditor(int auditorId);

	@Query("select a from Audition a where a.trip.id=?1")
	Collection<Audition> findByTripId(int id);

}
