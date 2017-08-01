package czar.evaluaciones.repositories;


import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import czar.evaluaciones.entities.Category;

@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Long> {

	Page<Category> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
	
	Set<Category> findByDescriptionIn(List<String> descriptions);
	
	List<Category> findByDescriptionContainingIgnoreCase(String descriptionLike);
}
