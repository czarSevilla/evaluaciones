package czar.evaluaciones.repositories;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import czar.evaluaciones.entities.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    @Query("select u from User u join u.authorities a where a.authority in (:roles)")
    Set<User> findByActiveRoles(@Param("roles") String... roles);
}
