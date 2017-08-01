package czar.evaluaciones.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import czar.evaluaciones.entities.Answer;

@Repository("answerRepository")
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
