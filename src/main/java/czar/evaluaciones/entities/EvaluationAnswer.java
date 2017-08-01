package czar.evaluaciones.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ev_evaluations_answers")
public class EvaluationAnswer implements Serializable {


    private static final long serialVersionUID = 1L;
    private Long idEvaluationAnswer;
    private Long idEvaluationQuestion;
    private Long idAnswer;
    private boolean correct;
    
    public EvaluationAnswer() {
     // Constructor default
    }
    
    public EvaluationAnswer(Long idEvaluationQuestion, Long idAnswer) {
    	this.idEvaluationQuestion = idEvaluationQuestion;
    	this.idAnswer = idAnswer;
    }

    @Id
    @Column(name = "id_evaluation_answer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getIdEvaluationAnswer() {
        return idEvaluationAnswer;
    }

    public void setIdEvaluationAnswer(Long idEvaluationAnswer) {
        this.idEvaluationAnswer = idEvaluationAnswer;
    }

    @Column(name = "id_evaluation_question", nullable = false)
    public Long getIdEvaluationQuestion() {
        return idEvaluationQuestion;
    }

    public void setIdEvaluationQuestion(Long idEvaluationQuestion) {
        this.idEvaluationQuestion = idEvaluationQuestion;
    }

    @Column(name = "id_answer", nullable = false)
    public Long getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(Long idAnswer) {
        this.idAnswer = idAnswer;
    }

    @Column(nullable = false)
    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
    
    
}
