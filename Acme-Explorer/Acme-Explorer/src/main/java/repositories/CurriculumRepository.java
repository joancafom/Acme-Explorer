
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {

	@Query("select t.ranger.curriculum from Trip t where t.id = ?1")
	Curriculum findByTripId(int id);

	@Query("select c from Curriculum c where c.ranger.id = ?1")
	Curriculum findByRangerId(int id);

}
