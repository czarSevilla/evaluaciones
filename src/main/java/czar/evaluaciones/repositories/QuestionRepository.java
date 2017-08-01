package czar.evaluaciones.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import czar.evaluaciones.entities.Question;
import czar.evaluaciones.enums.QuestionStatus;

@Repository("questionRepository")
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select q.idQuestion from Question q left join q.categories c where q.status = :status and c.idCategory in (:idsCategories)")
    List<Long> findIdsQuestionsByIdsCategories(@Param("status") QuestionStatus status, @Param("idsCategories") List<Long> idsCategories);
    
    Page<Question> findByStatus(QuestionStatus status, Pageable pageable);
    
    @Query("select q from Question q join q.categories c where c.description = :category")
    Page<Question> findByCategory(@Param("category") String category, Pageable pageable);
}
