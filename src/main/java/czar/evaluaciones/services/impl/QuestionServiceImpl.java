package czar.evaluaciones.services.impl;

import static czar.evaluaciones.enums.QuestionStatus.REVISION;
import static czar.evaluaciones.enums.QuestionStatus.ACTIVA;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;

import czar.evaluaciones.dtos.QuestionDto;
import czar.evaluaciones.entities.Answer;
import czar.evaluaciones.entities.Category;
import czar.evaluaciones.entities.Question;
import czar.evaluaciones.enums.Config;
import czar.evaluaciones.enums.QuestionStatus;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.repositories.AnswerRepository;
import czar.evaluaciones.repositories.CategoryRepository;
import czar.evaluaciones.repositories.ConfigurationRepository;
import czar.evaluaciones.repositories.QuestionRepository;
import czar.evaluaciones.services.QuestionService;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;
    
    private AnswerRepository answerRepository;
    
    private ConfigurationRepository configurationRepository;
    
    private CategoryRepository categoryRepository;
    
    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    
    @Autowired
    public void setAnswerRepository(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }
    
    @Autowired
    public void setConfigurationRepository(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }
    
    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    @Override
    public QuestionDto find(QuestionDto form) {
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
        Sort sort = new Sort(direction, "idQuestion");
        Pageable pageable = new PageRequest(numPage, size, sort);
        Page<Question> page = null;
        if (StringUtils.isNullOrEmpty(form.getSearchCategory())) {
        	Question qExample = new Question();
            qExample.setStatus(form.getSearchStatus());
            qExample.setText(form.getText());
            Example<Question> example = Example.of(qExample, ExampleMatcher.matching().withStringMatcher(StringMatcher.CONTAINING));
            page = questionRepository.findAll(example, pageable);  
        } else {
        	page = questionRepository.findByCategory(form.getSearchCategory(), pageable);
        }
              
        QuestionDto result = new QuestionDto();
        result.setQuestions(page.getContent());
        result.setStatus(form.getStatus());
        result.setDirection(dirStr);
        result.setPage(page.getNumber());
        result.setSize(page.getSize());
        result.setTotalPages(page.getTotalPages());
        result.setTotalItems(page.getTotalElements());
        result.setSearchStatus(form.getSearchStatus());
        result.setText(form.getText());
        result.setSearchCategory(form.getSearchCategory());
        return result;
    }

    @Override
    public void save(QuestionDto questionDto, Long idCreator) throws ServiceException {
        try {
        	Question question = null;
        	validate(questionDto);
        	if (questionDto.getIdQuestion() != null) {
        		question = questionRepository.findOne(questionDto.getIdQuestion());
        		if (question == null) {
        			throw new ServiceException("No se encontr\u00F3 el elemento con el identificador " + questionDto.getIdQuestion());
        		}
        		answerRepository.deleteInBatch(question.getAnswers());
        		question.setBody(questionDto.getBody());
        		question.setText(questionDto.getText());
        	} else {
        	    question = new Question();        	   
        		question.setText(questionDto.getText());
        		question.setBody(questionDto.getBody());
                question.setStatus(REVISION);
                question.setCreationDate(new Date());
                question.setIdCreator(idCreator);               
        	}
        	for (int i = 0; i < questionDto.getAnswersTexts().size(); i++) {
    	        Answer answer = new Answer();
    	        answer.setText(questionDto.getAnswersTexts().get(i));
    	        answer.setCorrect(questionDto.getAnswersCorrects().get(i) == 1);
    	        question.addAnswer(answer);
    	    }        	    
    	    Set<Category> savedCategories = categoryRepository.findByDescriptionIn(questionDto.getCategoriesDescriptions());
    	    Set<String> savedDescriptions = savedCategories.stream().map(cat -> cat.getDescription()).collect(Collectors.toSet());
    	    questionDto.getCategoriesDescriptions().removeAll(savedDescriptions);
    	    if (!questionDto.getCategoriesDescriptions().isEmpty()) {
    	    	for (String description : questionDto.getCategoriesDescriptions()) {
    	    		Category c = new Category();
    	    		c.setDescription(description);
    	    		categoryRepository.save(c);
    	    		savedCategories.add(c);
    	    	}
    	    }
    	    question.setCategories(savedCategories);
    	    questionRepository.save(question);
        } catch (DataIntegrityViolationException dve) {
            throw new ServiceException("Error al guardar pregunta: " + dve.getMessage());
        }

    }
    
    private void validate(QuestionDto questionDto) throws ServiceException {
    	if (questionDto.getAnswersTexts() == null || questionDto.getAnswersTexts().isEmpty()) {
	        throw new ServiceException("Debe proporcionar al menos una respuesta");
	    }
	    int corrects = 0;
	    for (Integer i : questionDto.getAnswersCorrects()) {
	        if (i == 1) {
	            corrects++;
	        }
	    }
	    if (corrects == 0) {
	        throw new ServiceException("Debe proporcionar al menos una respuesta correcta");
	    }
	    if (questionDto.getCategoriesDescriptions() == null || questionDto.getCategoriesDescriptions().isEmpty()) {
	        throw new ServiceException("Debe proporcionarl al menos una categor\u00EDa");
	    }
    }

    @Override
    public QuestionDto getById(Long idQuestion) {
        Question question = questionRepository.findOne(idQuestion);
        QuestionDto dto = new QuestionDto();
        BeanUtils.copyProperties(question, dto);
        List<String> answers = new ArrayList<>();
        List<Integer> corrects = new ArrayList<>();
        for (Answer answer : question.getAnswers()) {
        	answers.add(answer.getText());
        	corrects.add(answer.isCorrect() ? 1 : 0);
        }
        dto.setAnswersTexts(answers);
        dto.setAnswersCorrects(corrects);
        List<String> categories = new ArrayList<>();
        for (Category category : question.getCategories()) {
        	categories.add(category.getDescription());
        }
        dto.setCategoriesDescriptions(categories);
        return dto;
    }

    @Override
    public void changeStatus(Long idQuestion, QuestionStatus status) throws ServiceException {
    	Question question = questionRepository.findOne(idQuestion);
    	switch(status) {
	    	case REVISION:
	    		throw new ServiceException("No se permite cambiar a estatus 'REVISION'");
	    	case ACTIVA:
	    		if (question.getStatus() != REVISION) {
	    			throw new ServiceException("Solo se puede cambiar a 'ACTIVA' una pregunta en 'REVISION'");
	    		}
	    		break;
	    	case OBSOLETA:
	    		if (question.getStatus() != ACTIVA) {
	    			throw new ServiceException("Solo se puede cambiar a 'OBSOLETA' una pregunta 'ACTIVA'");
	    		}
	    		break;
    	}
    	question.setStatus(status);
    	questionRepository.save(question);
    }

}
