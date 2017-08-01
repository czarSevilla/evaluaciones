package czar.evaluaciones.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface EvaluationAnswerCustomRepository {

	void deleteAnswers(Set<Long> idsEvalQuestion);
	
	void updateAnswersCorrect(Long idEvaluation);
	
	List<String> loadAnswers(Set<Long> idsEvalQuestion);
	
	BigDecimal getResult(Long idEvaluation);
}
