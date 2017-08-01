package czar.evaluaciones.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import czar.evaluaciones.entities.Exam;

@Repository("examRepository")
public interface ExamRepository extends JpaRepository<Exam, Long> {

    Page<Exam> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
