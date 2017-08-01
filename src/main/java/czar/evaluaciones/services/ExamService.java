package czar.evaluaciones.services;

import czar.evaluaciones.dtos.ExamDto;
import czar.evaluaciones.exceptions.ServiceException;

public interface ExamService {

    ExamDto find(ExamDto examDto);
    
    void save(ExamDto exam) throws ServiceException;
    
    ExamDto getById(Long idExam);
    
    void delete(Long idExam) throws ServiceException;
}
