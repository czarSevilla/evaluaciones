package czar.evaluaciones.dtos;

import java.io.Serializable;

import czar.evaluaciones.entities.Answer;
import czar.evaluaciones.entities.EvaluationAnswer;

public class EvaluationAnswerDto extends EvaluationAnswer implements Serializable {

   private static final long serialVersionUID = 1L;
   
   private Answer answer;

   public Answer getAnswer() {
      return answer;
   }

   public void setAnswer(Answer answer) {
      this.answer = answer;
   }
}
