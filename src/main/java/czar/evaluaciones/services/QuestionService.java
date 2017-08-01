package czar.evaluaciones.services;

import czar.evaluaciones.dtos.QuestionDto;
import czar.evaluaciones.enums.QuestionStatus;
import czar.evaluaciones.exceptions.ServiceException;

public interface QuestionService {

    QuestionDto find(QuestionDto questionDto);
    
    void save(QuestionDto question, Long idCreator) throws ServiceException;
    
    QuestionDto getById(Long idQuestion);
    
    void changeStatus(Long idQuestion, QuestionStatus status) throws ServiceException;
}
