package czar.evaluaciones.services;

import java.util.List;

import czar.evaluaciones.dtos.ComboDto;

public interface ComboService {

    List<ComboDto> getComboExams();
    
    List<ComboDto> getComboCategories();
}
