
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	@Query("select a.audits from Auditor a where a.id=?1")
	Collection<Audit> findAuditsManagedByAuditor(int auditorId);

	@Query("select a from Audit a where a.trip.id=?1")
	Collection<Audit> findByTripId(int id);

	@Query("select a from Audit a where a.auditor.id = ?1 and a.trip.id = ?2")
	Audit findByAuditorIdAndTripId(int auditorId, int tripId);

}
