package czar.evaluaciones.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import czar.evaluaciones.entities.EvaluationQuestion;

@Repository("evaluationQuestionRepository")
public interface EvaluationQuestionRepository extends JpaRepository<EvaluationQuestion, Long> {

    Page<EvaluationQuestion> findByIdEvaluation(Long idEvaluation, Pageable pageable);
}
