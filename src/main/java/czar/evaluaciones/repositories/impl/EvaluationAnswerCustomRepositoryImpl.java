package czar.evaluaciones.repositories.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import czar.evaluaciones.repositories.EvaluationAnswerCustomRepository;

@Repository("evaluationAnswerCustomRepository")
public class EvaluationAnswerCustomRepositoryImpl implements EvaluationAnswerCustomRepository {
	
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEntityManager(EntityManager pEm) {
	    this.em = pEm;
	}

	@Override
	public void deleteAnswers(Set<Long> idsEvalQuestion) {
		String sql = "delete from EvaluationAnswer ea where ea.idEvaluationQuestion in (:idsEvalQuestion)";
		em.createQuery(sql).setParameter("idsEvalQuestion", idsEvalQuestion).executeUpdate();

	}

	@Override
	public void updateAnswersCorrect(Long idEvaluation) {
		StringBuilder sb = new StringBuilder();
		sb.append("update EvaluationAnswer ea set ea.correct = ");
		sb.append("(select aw.correct from Answer aw where aw.idAnswer = ea.idAnswer) ");
		sb.append("where ea.idEvaluationQuestion in (select eq.idEvaluationQuestion from ");
		sb.append("EvaluationQuestion eq where eq.idEvaluation = :idEvaluation)");
		em.createQuery(sb.toString()).setParameter("idEvaluation", idEvaluation).executeUpdate();
	}

    @Override
    public List<String> loadAnswers(Set<Long> idsEvalQuestion) {
        StringBuilder sb = new StringBuilder();
        sb.append("select concat('qe_', ea.idEvaluationQuestion, '_', ea.idAnswer) ");
        sb.append("from EvaluationAnswer ea where ea.idEvaluationQuestion in (:idsEvalQuestion)");
        return em.createQuery(sb.toString(), String.class)
                .setParameter("idsEvalQuestion", idsEvalQuestion)
                .getResultList();
    }

    @Override
    public BigDecimal getResult(Long idEvaluation) {
        String sql = "select ev_calculate_result_eval(:idEvaluation)";
        Float r = (Float) em.createNativeQuery(sql)
                .setParameter("idEvaluation", idEvaluation)
                .getSingleResult();
        return new BigDecimal(String.format("%f", r));
    }

}
