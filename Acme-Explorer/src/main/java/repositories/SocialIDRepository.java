package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.SocialID;

@Repository
public interface SocialIDRepository extends JpaRepository<SocialID, Integer>{

}
