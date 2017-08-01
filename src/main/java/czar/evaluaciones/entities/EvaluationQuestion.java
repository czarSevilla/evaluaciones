package czar.evaluaciones.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ev_evaluations_questions")
public class EvaluationQuestion implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idEvaluationQuestion;
    private Long idEvaluation;
    private Question question;
    
    public EvaluationQuestion() {
     // Constructor default
    }

    @Id
    @Column(name = "id_evaluation_question")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getIdEvaluationQuestion() {
        return idEvaluationQuestion;
    }

    public void setIdEvaluationQuestion(Long idEvaluationQuestion) {
        this.idEvaluationQuestion = idEvaluationQuestion;
    }

    @Column(name = "id_evaluation", nullable = false)
    public Long getIdEvaluation() {
        return idEvaluation;
    }

    public void setIdEvaluation(Long idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_question", nullable = false)
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    
}
