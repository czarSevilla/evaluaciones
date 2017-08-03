package czar.evaluaciones.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import czar.evaluaciones.entities.EvaluationAnswer;

@Repository("evaluationAnswerRepository")
public interface EvaluationAnswerRepository extends JpaRepository<EvaluationAnswer, Long> {
	
	List<EvaluationAnswer> findByIdEvaluationQuestion(Long idEvalQuestion);
}
