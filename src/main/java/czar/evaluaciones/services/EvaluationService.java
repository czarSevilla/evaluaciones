package czar.evaluaciones.services;

import java.util.List;

import czar.evaluaciones.dtos.EvaluationDto;
import czar.evaluaciones.dtos.GenerateEvalDto;
import czar.evaluaciones.dtos.ViewEvalDto;
import czar.evaluaciones.entities.Evaluation;

public interface EvaluationService {
    
    void generateEvaluation(GenerateEvalDto generarEvalDto, Long idManager);
    
    List<Evaluation> findPendingEvaluationsByIdUser(Long idUser);
    
    EvaluationDto loadEvaluation(Long idEvaluation, Long idApplicant, EvaluationDto prev);
    
    EvaluationDto loadEvaluation(Long idEvaluation);
    
    ViewEvalDto findEvaluations(ViewEvalDto viewEvalDto);
}
