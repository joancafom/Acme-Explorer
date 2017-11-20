package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ProfessionalRecord;

@Repository
public interface ProfessionalRecordRepository extends JpaRepository<ProfessionalRecord, Integer>{
	
	@Query("select pr from ProfessionalRecord pr where pr.curriculum.id=?1")
	Collection<ProfessionalRecord> findByCurriculumId(int id);

}
