package czar.evaluaciones.dtos;

import java.io.Serializable;
import java.util.List;

import czar.evaluaciones.entities.EvaluationQuestion;

public class EvaluationQuestionDto extends EvaluationQuestion implements Serializable {
   
   private static final long serialVersionUID = 1L;

   private List<EvaluationAnswerDto> responses;
   
   private boolean correct;

   public List<EvaluationAnswerDto> getResponses() {
      return responses;
   }

   public void setResponses(List<EvaluationAnswerDto> responses) {
      this.responses = responses;
   }

   public boolean isCorrect() {
      return correct;
   }

   public void setCorrect(boolean correct) {
      this.correct = correct;
   }
   
   

}
