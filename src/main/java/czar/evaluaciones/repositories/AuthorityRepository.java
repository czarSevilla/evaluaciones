package czar.evaluaciones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import czar.evaluaciones.entities.Authority;

@Repository("authorityRepository")
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
