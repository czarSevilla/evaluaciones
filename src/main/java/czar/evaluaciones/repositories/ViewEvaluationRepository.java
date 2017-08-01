package czar.evaluaciones.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import czar.evaluaciones.entities.ViewEvaluation;

@Repository("viewEvaluationRepository")
public interface ViewEvaluationRepository extends JpaRepository<ViewEvaluation, Long> {

	Page<ViewEvaluation> findByApplicantContainingIgnoreCase(String name, Pageable pageable);
}
