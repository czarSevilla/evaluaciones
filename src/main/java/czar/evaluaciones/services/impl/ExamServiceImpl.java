package czar.evaluaciones.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import czar.evaluaciones.dtos.ComboDto;
import czar.evaluaciones.dtos.ExamDto;
import czar.evaluaciones.entities.Category;
import czar.evaluaciones.entities.Exam;
import czar.evaluaciones.enums.Config;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.repositories.ConfigurationRepository;
import czar.evaluaciones.repositories.ExamRepository;
import czar.evaluaciones.services.ComboService;
import czar.evaluaciones.services.ExamService;

@Service("examService")
public class ExamServiceImpl implements ExamService {
    
    @Autowired
    private ExamRepository examRepository;
    
    @Autowired
    private ConfigurationRepository configurationRepository;
    
    @Autowired
    private ComboService comboService;

    @Override
    public ExamDto find(ExamDto form) {
        String dirStr = Direction.DESC.toString();
        int numPage = 0;
        int size = Integer.parseInt(configurationRepository.findByKey(Config.ROWS_X_PAGE.toString()).getValue());
        if (form.getDirection() != null) {
            dirStr = form.getDirection();
        }
        if (form.getPage() > 0) {
            numPage = form.getPage();
        }
        if (form.getSize() > 0) {
            size = form.getSize();
        }
        Direction direction = Direction.valueOf(dirStr);
        Sort sort = new Sort(direction, "name");
        Pageable pageable = new PageRequest(numPage, size, sort);
        Page<Exam> page = null;
        if (form.getName() == null || StringUtils.isEmpty(form.getName())) {
            page = examRepository.findAll(pageable);
        } else {
            page = examRepository.findByNameContainingIgnoreCase(form.getName(), pageable);
        }
        ExamDto result = new ExamDto();
        result.setExams(page.getContent());
        result.setName(form.getName());
        result.setDirection(dirStr);
        result.setPage(page.getNumber());
        result.setSize(page.getSize());
        result.setTotalPages(page.getTotalPages());
        result.setTotalItems(page.getTotalElements());
        return result;
    }

    @Override
    public void save(ExamDto examDto) throws ServiceException {
        try {
        	Exam exam = new Exam();
        	BeanUtils.copyProperties(examDto, exam);
        	BigDecimal percent = exam.getPassPercent().multiply(new BigDecimal("0.01"));
        	exam.setPassPercent(percent);
        	Set<Category> categories = examDto.getIdsCategories()
        			.stream().map(id -> new Category(id)).collect(Collectors.toSet());
        	exam.setCategories(categories);
            examRepository.save(exam);
        } catch (DataIntegrityViolationException dve) {
            throw new ServiceException("Ya existe un registro con el mismo nombre");
        }
    }

    @Override
    public ExamDto getById(Long idExam) {
    	Exam exam = examRepository.findOne(idExam);
    	ExamDto examDto = new ExamDto();
    	BeanUtils.copyProperties(exam, examDto);
    	List<ComboDto> categories = comboService.getComboCategories();
    	List<ComboDto> selectedCategories = exam.getCategories().stream().map(cat -> new ComboDto(cat.getIdCategory(), cat.getDescription())).collect(Collectors.toList());
    	categories.removeAll(selectedCategories);
    	examDto.setComboCategories(categories);
    	examDto.setPassPercent(exam.getPassPercent().multiply(new BigDecimal("100")));
        return examDto;
    }

    @Override
    public void delete(Long idExam) throws ServiceException {
        try {
            examRepository.delete(idExam);
        } catch (DataIntegrityViolationException dve) {
            throw new ServiceException("El registro tiene dependencias que impiden su eliminaci\u00F3n");
        }
    }

}
