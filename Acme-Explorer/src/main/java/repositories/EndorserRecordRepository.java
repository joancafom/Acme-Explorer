package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EndorserRecord;

@Repository
public interface EndorserRecordRepository extends JpaRepository<EndorserRecord, Integer>{
	
	@Query("select er from EndorserRecord er where er.curriculum.id=?1")
	Collection<EndorserRecord> findByCurriculumId(int id);

}
