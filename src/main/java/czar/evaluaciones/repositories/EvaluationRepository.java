package czar.evaluaciones.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import czar.evaluaciones.entities.Evaluation;
import czar.evaluaciones.enums.EvaluationStatus;

@Repository("evaluationRepository")
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Query("select ev from Evaluation ev where ev.idApplicant = :id and ev.status not in (:status1, :status2) and ev.expirationDate >= :expDate")
    List<Evaluation> findAllPending(@Param("id") Long idUser, @Param("status1") EvaluationStatus status1, @Param("status2") EvaluationStatus status2, @Param("expDate") Date expirationDate);
}
