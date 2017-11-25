
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	@Query("select n from Note n where n.auditor.id = ?1 and n.trip.manager.id = ?2")
	Collection<Note> findByAuditorAndManagerIds(int auditorId, int managerId);

	@Query("select n from Note n where n.auditor.id = ?1")
	Collection<Note> findByAuditorId(int auditorId);

	@Query("select n from Note n where n.trip.manager.id = ?1")
	Collection<Note> findByManagerId(int managerId);
}
