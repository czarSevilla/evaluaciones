package czar.evaluaciones.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import czar.evaluaciones.dtos.ComboDto;
import czar.evaluaciones.repositories.CategoryRepository;
import czar.evaluaciones.repositories.ExamRepository;
import czar.evaluaciones.services.ComboService;

@Service("comboService")
public class ComboServiceImpl implements ComboService {

    @Autowired
    private ExamRepository examRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    
    @Override
    public List<ComboDto> getComboExams() {
        return examRepository.findAll().stream()
                .map(exam -> new ComboDto(exam.getIdExam(), exam.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ComboDto> getComboCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> new ComboDto(category.getIdCategory(), category.getDescription()))
                .collect(Collectors.toList());
    }

}
