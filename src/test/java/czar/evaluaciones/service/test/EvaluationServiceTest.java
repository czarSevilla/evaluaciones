package czar.evaluaciones.service.test;

import static czar.evaluaciones.enums.EvaluationStatus.AGOTADA;
import static czar.evaluaciones.enums.EvaluationStatus.APLICADA;
import static czar.evaluaciones.enums.EvaluationStatus.EJECUCION;
import static czar.evaluaciones.enums.EvaluationStatus.PENDIENTE;
import static czar.evaluaciones.enums.QuestionStatus.ACTIVA;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import czar.evaluaciones.dtos.EvaluationDto;
import czar.evaluaciones.dtos.GenerateEvalDto;
import czar.evaluaciones.dtos.ViewEvalDto;
import czar.evaluaciones.entities.Category;
import czar.evaluaciones.entities.Configuration;
import czar.evaluaciones.entities.Evaluation;
import czar.evaluaciones.entities.EvaluationAnswer;
import czar.evaluaciones.entities.EvaluationQuestion;
import czar.evaluaciones.entities.Exam;
import czar.evaluaciones.entities.User;
import czar.evaluaciones.entities.ViewEvaluation;
import czar.evaluaciones.enums.Config;
import czar.evaluaciones.enums.SourceEvaluation;
import czar.evaluaciones.repositories.ConfigurationRepository;
import czar.evaluaciones.repositories.EvaluationAnswerCustomRepository;
import czar.evaluaciones.repositories.EvaluationAnswerRepository;
import czar.evaluaciones.repositories.EvaluationQuestionRepository;
import czar.evaluaciones.repositories.EvaluationRepository;
import czar.evaluaciones.repositories.ExamRepository;
import czar.evaluaciones.repositories.NotificationRepository;
import czar.evaluaciones.repositories.QuestionRepository;
import czar.evaluaciones.repositories.UserRepository;
import czar.evaluaciones.repositories.ViewEvaluationRepository;
import czar.evaluaciones.services.EvaluationService;
import czar.evaluaciones.services.impl.EvaluationServiceImpl;

@RunWith(SpringRunner.class)
public class EvaluationServiceTest {
	
	@TestConfiguration
    static class EvaluationServiceTestConfiguration {
        @Bean
        public EvaluationService getEvaluationService() {
            return new EvaluationServiceImpl();
        }
    }
	
	@Autowired
	private EvaluationService evaluationService;
	
	@MockBean
    private ExamRepository examRepository;
    
	@MockBean
    private UserRepository userRepository;
    
	@MockBean
    private PasswordEncoder passwordEncoder;
    
	@MockBean
    private ConfigurationRepository configurationRepository;
    
	@MockBean
    private EvaluationRepository evaluationRepository;
    
	@MockBean
    private EvaluationQuestionRepository evaluationQuestionRepository;
    
	@MockBean
    private QuestionRepository questionRepository;
    
	@MockBean
    private NotificationRepository notificationRepository;
    
	@MockBean
    private EvaluationAnswerRepository evaluationAnswerRepository;
    
	@MockBean
    private EvaluationAnswerCustomRepository evaluationAnswerCustomRepository;
    
	@MockBean
    private ViewEvaluationRepository viewEvaluationRepository;
	
	@Before
    public void init() {
		Configuration config1 = new Configuration();
		config1.setIdConfiguration(1L);
		config1.setKey(Config.EXPIRATION_DAY.toString());
		config1.setValue("5");
		
		Configuration config2 = new Configuration();
		config2.setIdConfiguration(2L);
		config2.setKey(Config.QUESTIONS_X_PAGE.toString());
		config2.setValue("1");
		
		Configuration config3 = new Configuration();
		config3.setIdConfiguration(3L);
		config3.setKey(Config.ROWS_X_PAGE.toString());
		config3.setValue("10");
		
		when(configurationRepository.findByKey(eq(Config.EXPIRATION_DAY.toString()))).thenReturn(config1);
		
		when(configurationRepository.findByKey(eq(Config.QUESTIONS_X_PAGE.toString()))).thenReturn(config2);
		
		when(configurationRepository.findByKey(eq(Config.ROWS_X_PAGE.toString()))).thenReturn(config3);
		
		when(questionRepository.findIdsQuestionsByIdsCategories(eq(ACTIVA), anyListOf(Long.class))).thenReturn(Arrays.asList(1L, 2L, 3L));
		
		User user = new User();
		when(userRepository.findByUsername(eq("czar.sevilla@gmail.com"))).thenReturn(user);
		
		Exam exam = new Exam();
		exam.setQuestions(2);
		exam.setPassPercent(new BigDecimal("0.60"));
		exam.setName("examName");
		exam.setExamMinutes(120);
		Category category = new Category();
		category.setIdCategory(1L);
		category.setDescription("category");
		exam.getCategories().add(category);
		when(examRepository.findOne(eq(1L))).thenReturn(exam);
		
		Evaluation evaluation = new Evaluation();
		evaluation.setIdEvaluation(1L);
		evaluation.setIdApplicant(1L);
		evaluation.setCurrentPage(1);
		evaluation.setQuestionsPerPage(1);
		evaluation.setStatus(PENDIENTE);
		when(evaluationRepository.findOne(eq(1L))).thenReturn(evaluation);
		
		Evaluation evaluation1 = new Evaluation();
		evaluation1.setIdEvaluation(2L);
		evaluation1.setIdApplicant(2L);
		evaluation1.setCurrentPage(1);
		evaluation1.setQuestionsPerPage(1);
		evaluation1.setCurrentPage(1);
		evaluation1.setStatus(EJECUCION);
		when(evaluationRepository.findOne(eq(2L))).thenReturn(evaluation1);
		
		Evaluation evaluation2 = new Evaluation();
		evaluation2.setIdEvaluation(3L);
		evaluation2.setIdApplicant(3L);
		evaluation2.setCurrentPage(1);
		evaluation2.setQuestionsPerPage(1);
		evaluation2.setCurrentPage(1);
		evaluation2.setStatus(EJECUCION);
		when(evaluationRepository.findOne(eq(3L))).thenReturn(evaluation2);
		
		
		@SuppressWarnings("unchecked")
		Page<EvaluationQuestion> pageEvalQ = mock(Page.class);
		when(evaluationQuestionRepository.findByIdEvaluation(eq(1L), any(Pageable.class))).thenReturn(pageEvalQ);
		
		@SuppressWarnings("unchecked")
		Page<EvaluationQuestion> pageEa1 = mock(Page.class);
		when(evaluationQuestionRepository.findByIdEvaluation(eq(2L), any(Pageable.class))).thenReturn(pageEa1);
		
		@SuppressWarnings("unchecked")
		Page<EvaluationQuestion> pageEa2 = mock(Page.class);
		when(evaluationQuestionRepository.findByIdEvaluation(eq(3L), any(Pageable.class))).thenReturn(pageEa2);
		
		EvaluationQuestion eq = new EvaluationQuestion();
		eq.setIdEvaluationQuestion(1L);
		when(pageEvalQ.hasContent()).thenReturn(true);
		when(pageEvalQ.getContent()).thenReturn(Arrays.asList(eq));
		
		@SuppressWarnings("unchecked")
		Page<ViewEvaluation> page = mock(Page.class);
		when(viewEvaluationRepository.findAll(any(Pageable.class))).thenReturn(page);
		
		when(viewEvaluationRepository.findAll(any(), any(Pageable.class))).thenReturn(page);
		
	}
	
	@Test
	public void generateEvaluationTest() {
		Long idManager = 1L;
		GenerateEvalDto generarEvalDto = new GenerateEvalDto();
		generarEvalDto.setSource(SourceEvaluation.CATEGORIES);
		generarEvalDto.setIdsCategories(Arrays.asList(1L, 2L, 3L));
		generarEvalDto.setQuestions(20);
		generarEvalDto.setPassPercent(60);
		generarEvalDto.setName("evaluationByCategories");
		generarEvalDto.setEvalMinutes(120);
		generarEvalDto.setEmail("czar.sevilla@gmail.com");
		
		evaluationService.generateEvaluation(generarEvalDto, idManager);
		
		verify(configurationRepository, atLeastOnce()).findByKey(anyString());
		verify(questionRepository, atLeastOnce()).findIdsQuestionsByIdsCategories(eq(ACTIVA), anyListOf(Long.class));
		verify(userRepository, atLeastOnce()).findByUsername(anyString());
		
	}
	
	@Test
	public void generarEvaluationEa1Test() {
		Long idManager = 1L;
		GenerateEvalDto generarEvalDto = new GenerateEvalDto();
		generarEvalDto.setSource(SourceEvaluation.EXAM);
		generarEvalDto.setEmail("czarsevilla@gmail.com");
		generarEvalDto.setIdExam(1L);
		
		evaluationService.generateEvaluation(generarEvalDto, idManager);
	}
	
	@Test
	public void findPendingEvaluationsByIdUserTest() {
		Long idUser = 1L;
		
		evaluationService.findPendingEvaluationsByIdUser(idUser);
		
		verify(evaluationRepository, atLeastOnce())
			.findAllPending(eq(idUser), eq(APLICADA), eq(AGOTADA) , any(Date.class));
	}
	
	@Test
	public void loadEvaluationTest() {
		Long idEvaluation = 1L;
		Long idApplicant = 1L;
		EvaluationDto prev = new EvaluationDto();
		prev.setStep(1);
		
		evaluationService.loadEvaluation(idEvaluation, idApplicant, prev);
		
		verify(evaluationRepository, atLeastOnce()).findOne(anyLong());
		verify(evaluationRepository, atLeastOnce()).save(any(Evaluation.class));
		verify(evaluationQuestionRepository, atLeastOnce()).findByIdEvaluation(anyLong(), any(Pageable.class));
		verify(evaluationAnswerCustomRepository, atLeastOnce()).loadAnswers(anySetOf(Long.class));
	}
	
	@Test
	public void loadEvaluationEa1Test() {
		Long idEvaluation = 20L;
		Long idApplicant = 1L;
		EvaluationDto prev = new EvaluationDto();
		
		EvaluationDto result = evaluationService.loadEvaluation(idEvaluation, idApplicant, prev);
		
		assertThat(result.getErrorMessage(), containsString("La evaluaci\u00F3n no se encontr\u00F3"));
	}
	
	@Test
	public void loadEvaluationEa2Test() {
		Long idEvaluation = 1L;
		Long idApplicant = 2L;
		EvaluationDto prev = new EvaluationDto();
		
		EvaluationDto result = evaluationService.loadEvaluation(idEvaluation, idApplicant, prev);
		
		assertThat(result.getErrorMessage(), containsString("La evaluaci\u00F3n no corresponde al usuario"));
	}
	
	@Test
	public void loadEvaluationEa3Test() {
		Long idEvaluation = 2L;
		Long idApplicant = 2L;
		EvaluationDto prev = new EvaluationDto();
		prev.setStep(1);
		
		evaluationService.loadEvaluation(idEvaluation, idApplicant, prev);
		
		verify(evaluationRepository, atLeastOnce()).findOne(anyLong());
		verify(evaluationQuestionRepository, atLeastOnce()).findByIdEvaluation(anyLong(), any(Pageable.class));
	}
	
	@Test
	public void loadEvaluationEa4Test() {
		Long idEvaluation = 2L;
		Long idApplicant = 2L;
		EvaluationDto prev = new EvaluationDto();
		prev.setStep(2);
		prev.setCurrentPage(1);
		
		evaluationService.loadEvaluation(idEvaluation, idApplicant, prev);
		
		verify(evaluationRepository, atLeastOnce()).findOne(anyLong());
		verify(evaluationQuestionRepository, atLeastOnce()).findByIdEvaluation(anyLong(), any(Pageable.class));
		verify(evaluationAnswerCustomRepository, atLeastOnce()).deleteAnswers(anySetOf(Long.class));
	}
	
	@Test
	public void loadEvaluationEa5Test() {
		Long idEvaluation = 3L;
		Long idApplicant = 3L;
		EvaluationDto prev = new EvaluationDto();
		prev.setStep(3);
		prev.setCurrentPage(1);
		prev.setAnswers(Arrays.asList("answer_1_1"));
		prev.setTimeout(true);
		
		evaluationService.loadEvaluation(idEvaluation, idApplicant, prev);
		
		verify(evaluationRepository, atLeastOnce()).findOne(anyLong());
		verify(evaluationAnswerRepository, atLeastOnce()).save(anyListOf(EvaluationAnswer.class));
	}
	
	@Test
	public void loadEvaluationEa6Test() {
		Long idEvaluation = 3L;
		Long idApplicant = 3L;
		EvaluationDto prev = new EvaluationDto();
		prev.setStep(3);
		prev.setCurrentPage(1);
		prev.setAnswers(new ArrayList<>());
		prev.setTimeout(false);
		
		evaluationService.loadEvaluation(idEvaluation, idApplicant, prev);
		
		verify(evaluationRepository, atLeastOnce()).findOne(anyLong());
	}
	
	@Test
	public void findEvaluationsTest() {		
		ViewEvalDto viewEvalDto = new ViewEvalDto();

		evaluationService.findEvaluations(viewEvalDto);
		
		verify(configurationRepository, atLeastOnce()).findByKey(eq(Config.ROWS_X_PAGE.toString()));
		verify(viewEvaluationRepository, atLeastOnce()).findAll(any(), any(Pageable.class));
	}
	
	@Test
	public void findEvaluationsEa1Test() {		
		ViewEvalDto viewEvalDto = new ViewEvalDto();
		viewEvalDto.setSize(10);
		viewEvalDto.setDirection("ASC");
		viewEvalDto.setPropertySort("idEvaluation");
		viewEvalDto.setName("name");
		viewEvalDto.setSearchStatus(PENDIENTE);
		viewEvalDto.setSearchResultado("resultado");
		viewEvalDto.setSearchManager("manager");

		evaluationService.findEvaluations(viewEvalDto);
		
		verify(configurationRepository, atLeastOnce()).findByKey(eq(Config.ROWS_X_PAGE.toString()));
		verify(viewEvaluationRepository, atLeastOnce()).findAll(any(), any(Pageable.class));
	}
	
}
