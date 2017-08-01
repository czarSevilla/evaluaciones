package czar.evaluaciones.service.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import czar.evaluaciones.dtos.QuestionDto;
import czar.evaluaciones.entities.Answer;
import czar.evaluaciones.entities.Category;
import czar.evaluaciones.entities.Configuration;
import czar.evaluaciones.entities.Question;
import czar.evaluaciones.enums.Config;
import czar.evaluaciones.enums.QuestionStatus;
import czar.evaluaciones.exceptions.ServiceException;
import czar.evaluaciones.repositories.AnswerRepository;
import czar.evaluaciones.repositories.CategoryRepository;
import czar.evaluaciones.repositories.ConfigurationRepository;
import czar.evaluaciones.repositories.QuestionRepository;
import czar.evaluaciones.services.QuestionService;
import czar.evaluaciones.services.impl.QuestionServiceImpl;

@RunWith(SpringRunner.class)
public class QuestionServiceTest {

    @TestConfiguration
    static class QuestionServiceTestConfiguration {
        @Bean
        public QuestionService getQuestionService() {
            return new QuestionServiceImpl();
        }
    }
    
    @Autowired
    private QuestionService questionService;
    
    @MockBean
    private QuestionRepository questionRepository;
    
    @MockBean
    private AnswerRepository answerRepository;
    
    @MockBean
    private ConfigurationRepository configurationRepository;
    
    @MockBean
    private CategoryRepository categoryRepository;
    
    @SuppressWarnings("unchecked")
    @Before
    public void init() {
        Configuration config2 = new Configuration();
        config2.setIdConfiguration(2L);
        config2.setKey(Config.ROWS_X_PAGE.toString());
        config2.setValue("10");
        
        when(configurationRepository.findByKey(eq(Config.ROWS_X_PAGE.toString()))).thenReturn(config2);
        
        Page<Question> page = mock(Page.class);
        when(questionRepository.findAll(any(), any(Pageable.class))).thenReturn(page);
        when(questionRepository.findByCategory(anyString(), any(Pageable.class))).thenReturn(page);
        
        Question question = new Question();
        Answer answer = new Answer();
        answer.setText("respuesta 1");
        answer.setCorrect(false);
        Answer answerCorrect = new Answer();
        answerCorrect.setText("respuesta 2");
        answerCorrect.setCorrect(true);
        Set<Answer> answers = new HashSet<>();
        answers.add(answer);
        answers.add(answerCorrect);
        question.setAnswers(answers);
        Category category = new Category();
        category.setDescription("category");
        category.setIdCategory(1L);
        Set<Category> categories = new HashSet<>();
        categories.add(category);
        question.setCategories(categories);
        question.setStatus(QuestionStatus.REVISION);
        
        when(questionRepository.findOne(eq(1L))).thenReturn(question);
        
        Question question2 = new Question();
        question2.setStatus(QuestionStatus.ACTIVA);
        
        when(questionRepository.findOne(eq(2L))).thenReturn(question2);
        
        when(questionRepository.findOne(eq(3L))).thenThrow(DataIntegrityViolationException.class);
    }
    
    @Test
    public void findTest() {
        QuestionDto questionDto = new QuestionDto();
        
        questionService.find(questionDto);
        
        verify(configurationRepository, atLeastOnce()).findByKey(eq(Config.ROWS_X_PAGE.toString()));
        verify(questionRepository, atLeastOnce()).findAll(any(), any(Pageable.class));
    }
    
    @Test
    public void findEa1Test() {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setDirection("ASC");
        questionDto.setPage(1);
        questionDto.setSize(10);
        questionDto.setSearchCategory("category");
        
        questionService.find(questionDto);
        
        verify(configurationRepository, atLeastOnce()).findByKey(eq(Config.ROWS_X_PAGE.toString()));
        verify(questionRepository, atLeastOnce()).findByCategory(anyString(), any(Pageable.class));
    }

    
    @Test
    public void saveTest() throws ServiceException {
        QuestionDto question = new QuestionDto();
        question.setAnswersTexts(Arrays.asList("respuesta 1"));
        question.setAnswersCorrects(Arrays.asList(1));
        question.setCategoriesDescriptions(Arrays.asList("category"));
        Long idCreator = 1L;
        
        questionService.save(question, idCreator);
        
        verify(questionRepository, atLeastOnce()).save(any(Question.class));
        
    }
    
    @Test
    public void saveEa1Test() throws ServiceException {
        QuestionDto question = new QuestionDto();
        question.setAnswersTexts(Arrays.asList("respuesta 1", "respuesta 2"));
        question.setAnswersCorrects(Arrays.asList(1, 0));
        question.setCategoriesDescriptions(Arrays.asList("category"));
        question.setIdQuestion(1L);
        Long idCreator = 1L;
        
        questionService.save(question, idCreator);
        
        verify(questionRepository, atLeastOnce()).findOne(anyLong());
        verify(questionRepository, atLeastOnce()).save(any(Question.class));
        
    }
    
    @Test
    public void saveEa2Test() {
        QuestionDto question = new QuestionDto();
        question.setAnswersTexts(Arrays.asList("respuesta 1", "respuesta 2"));
        question.setAnswersCorrects(Arrays.asList(1, 0));
        question.setCategoriesDescriptions(Arrays.asList("category"));
        question.setIdQuestion(20L);
        Long idCreator = 1L;
        ServiceException exception = null;
        try {
            questionService.save(question, idCreator);
        } catch (ServiceException ex) {
            exception = ex;
        }
        
        verify(questionRepository, atLeastOnce()).findOne(anyLong());

        assertThat(exception, notNullValue());
        assertThat(exception.getMessage(), containsString("No se encontr\u00F3 el elemento con el identificador 2"));
    }
    
    @Test(expected = ServiceException.class)
    public void saveEa3Test() throws ServiceException {
        QuestionDto question = new QuestionDto();
        Long idCreator = 1L;
        
        questionService.save(question, idCreator);
    }
    
    @Test(expected = ServiceException.class)
    public void saveEa4Test() throws ServiceException {
        QuestionDto question = new QuestionDto();
        question.setAnswersTexts(new ArrayList<>());
        Long idCreator = 1L;
        
        questionService.save(question, idCreator);
    }
    
    @Test(expected = ServiceException.class)
    public void saveEa5Test() throws ServiceException {
        QuestionDto question = new QuestionDto();
        question.setAnswersTexts(Arrays.asList("respuesta"));
        question.setAnswersCorrects(Arrays.asList(0));
        Long idCreator = 1L;
        
        questionService.save(question, idCreator);
    }
    
    @Test(expected = ServiceException.class)
    public void saveEa6Test() throws ServiceException {
        QuestionDto question = new QuestionDto();
        question.setAnswersTexts(Arrays.asList("respuesta"));
        question.setAnswersCorrects(Arrays.asList(1));
        Long idCreator = 1L;
        
        questionService.save(question, idCreator);
    }
    
    @Test(expected = ServiceException.class)
    public void saveEa7Test() throws ServiceException {
        QuestionDto question = new QuestionDto();
        question.setAnswersTexts(Arrays.asList("respuesta"));
        question.setAnswersCorrects(Arrays.asList(1));
        question.setCategoriesDescriptions(new ArrayList<>());
        Long idCreator = 1L;
        
        questionService.save(question, idCreator);
    }
    
    @Test(expected = ServiceException.class)
    public void saveEa8Test() throws ServiceException {
        QuestionDto question = new QuestionDto();
        question.setIdQuestion(3L);
        question.setAnswersTexts(Arrays.asList("respuesta"));
        question.setAnswersCorrects(Arrays.asList(1));
        question.setCategoriesDescriptions(Arrays.asList("category"));
        Long idCreator = 1L;
        
        questionService.save(question, idCreator);
    }
    
    @Test
    public void getByIdTest() {
        Long idQuestion = 1L;
        
        questionService.getById(idQuestion);
        
        verify(questionRepository, atLeastOnce()).findOne(anyLong());
    }
    
    @Test(expected = ServiceException.class)
    public void changeStatusTest() throws ServiceException {
        Long idQuestion = 1L;
        QuestionStatus status = QuestionStatus.REVISION;
        questionService.changeStatus(idQuestion, status);
    }
    
    @Test
    public void changeStatusEa1Test() throws ServiceException {
        Long idQuestion = 1L;
        QuestionStatus status = QuestionStatus.ACTIVA;
        
        questionService.changeStatus(idQuestion, status);
        
        verify(questionRepository, atLeastOnce()).save(any(Question.class));
        
    }
    
    @Test
    public void changeStatusEa2Test() throws ServiceException {
        Long idQuestion = 2L;
        QuestionStatus status = QuestionStatus.OBSOLETA;
        
        questionService.changeStatus(idQuestion, status);
        
        verify(questionRepository, atLeastOnce()).save(any(Question.class));
        
    }
    
    @Test(expected = ServiceException.class)
    public void changeStatusEa3Test() throws ServiceException {
        Long idQuestion = 1L;
        QuestionStatus status = QuestionStatus.OBSOLETA;
        
        questionService.changeStatus(idQuestion, status);
        
    }
    
    @Test(expected = ServiceException.class)
    public void changeStatusEa4Test() throws ServiceException {
        Long idQuestion = 2L;
        QuestionStatus status = QuestionStatus.ACTIVA;
        
        questionService.changeStatus(idQuestion, status);
        
    }
    
}
