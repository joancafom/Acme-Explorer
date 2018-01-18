
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EducationRecord;

@Repository
public interface EducationRecordRepository extends JpaRepository<EducationRecord, Integer> {

	@Query("select er from EducationRecord er where er.curriculum.id = ?1")
	Collection<EducationRecord> findByCurriculumId(int id);

}
