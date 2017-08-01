package czar.evaluaciones.dtos.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import czar.evaluaciones.dtos.CategoryCloudDto;
import czar.evaluaciones.dtos.CategoryDto;
import czar.evaluaciones.dtos.ComboDto;
import czar.evaluaciones.dtos.EvaluationDto;
import czar.evaluaciones.dtos.ExamDto;
import czar.evaluaciones.dtos.GenerateEvalDto;
import czar.evaluaciones.dtos.PaginadorDto;
import czar.evaluaciones.dtos.QuestionDto;
import czar.evaluaciones.dtos.ViewEvalDto;
import czar.evaluaciones.entities.Answer;
import czar.evaluaciones.entities.Authority;
import czar.evaluaciones.entities.Category;
import czar.evaluaciones.entities.EvaluationQuestion;
import czar.evaluaciones.entities.Exam;
import czar.evaluaciones.entities.Question;
import czar.evaluaciones.entities.User;
import czar.evaluaciones.entities.ViewEvaluation;
import czar.evaluaciones.enums.EvaluationStatus;
import czar.evaluaciones.enums.QuestionStatus;
import czar.evaluaciones.enums.SourceEvaluation;

public class DtosTest {

	@Test
	public void categoryCloudDtoTest(){
		CategoryCloudDto categoryCloudDto = new CategoryCloudDto();
		Integer value = 1;
		String key = "key";
		CategoryCloudDto category = new CategoryCloudDto("key", value);

		categoryCloudDto.setLabel(key);
		categoryCloudDto.setStrength(value);
		
		assertThat(categoryCloudDto.getLabel(), equalTo(key));
		assertThat(categoryCloudDto.getStrength(), equalTo(value));
		assertThat(category.getLabel(), equalTo(key));
		assertThat(category.getStrength(), equalTo(value));
		
	}
	
	@Test
	public void categoryDtoTest(){
		CategoryDto categoryDto = new CategoryDto();
		List<Category> categories = new ArrayList<>();
		String description = "description";
		String direction = "direction";
		int page = 1;
		int size = 1;
		Long totalItems = 1L;
		int totalPages = 1;
		
		categoryDto.setCategories(categories);
		categoryDto.setDescription(description);
		categoryDto.setDirection(direction);
		categoryDto.setPage(page);
		categoryDto.setSize(size);
		categoryDto.setTotalItems(totalItems);
		categoryDto.setTotalPages(totalPages);
		
		assertThat(categoryDto.getCategories(), equalTo(categories));
		assertThat(categoryDto.getDescription(), equalTo(description));
		assertThat(categoryDto.getDirection(), equalTo(direction));
		assertThat(categoryDto.getPage(), equalTo(page));
		assertThat(categoryDto.getSize(), equalTo(size));
		assertThat(categoryDto.getTotalItems(), equalTo(totalItems));
		assertThat(categoryDto.getTotalPages(), equalTo(totalPages));
	}
	
	@Test
	public void comboDtoTest(){
		ComboDto comboDto = new ComboDto();
		EvaluationDto evaluation = new EvaluationDto();
		Long key = 1L;
		String value = "value";
		ComboDto combo = new ComboDto(key, value);
		
		comboDto.setKey(key);
		comboDto.setValue(value);
		
		assertThat(comboDto.getKey(), equalTo(key));
		assertThat(comboDto.getValue(), equalTo(value));
		
		assertThat(combo.getKey(), equalTo(key));
		assertThat(combo.getValue(), equalTo(value));
		
		assertFalse(combo.equals(null));
		assertFalse(combo.equals(evaluation));
		assertTrue(combo.equals(comboDto));
		assertTrue(combo.hashCode()==comboDto.hashCode());
	}
	
	@Test
	public void evaluationDto(){
		EvaluationDto evaluationDto = new EvaluationDto();
		List<String> answers = new ArrayList<>();
		Set<Category> categories = new HashSet<>();
		Date creationDate = new Date();
		Integer currentPage = 1;
		Date endTime = new Date();
		boolean error = false;
		String errorMessage = "errorMessage";
		int evalMinutes = 1;
		Date expirationDate = new Date();
		boolean finish = true;
		Long idApplicant = 1L;
		Long idEvaluation = 1L;
		Set<Long> idEvaluationsQuestions = new HashSet<>();
		Long idExam = 1L;
		Long idGenerator = 1L;
		String name = "name";
		PaginadorDto paginador =  new PaginadorDto();
		BigDecimal passPercent = BigDecimal.ONE;
		List<EvaluationQuestion> questionList = new ArrayList<>();
		Integer questions = 1;
		Integer questionsPerPage = 1;
		Date startTime = new Date();
		EvaluationStatus status = EvaluationStatus.AGOTADA;
		int step = 1;
		boolean timeout = true;
		
		evaluationDto.setAnswers(answers);
		evaluationDto.setCategories(categories);
		evaluationDto.setCreationDate(creationDate);
		evaluationDto.setCurrentPage(currentPage);
		evaluationDto.setEndTime(endTime);
		evaluationDto.setError(error);
		evaluationDto.setErrorMessage(errorMessage);
		evaluationDto.setEvalMinutes(evalMinutes);
		evaluationDto.setExpirationDate(expirationDate);
		evaluationDto.setFinish(finish);
		evaluationDto.setIdApplicant(idApplicant);
		evaluationDto.setIdEvaluation(idEvaluation);
		evaluationDto.setIdEvaluationsQuestions(idEvaluationsQuestions);
		evaluationDto.setIdExam(idExam);
		evaluationDto.setIdGenerator(idGenerator);
		evaluationDto.setName(name);
		evaluationDto.setPaginador(paginador);
		evaluationDto.setPassPercent(passPercent);
		evaluationDto.setQuestionList(questionList);
		evaluationDto.setQuestions(questions);
		evaluationDto.setQuestionsPerPage(questionsPerPage);
		evaluationDto.setStartTime(startTime);
		evaluationDto.setStatus(status);
		evaluationDto.setStep(step);
		evaluationDto.setTimeout(timeout);
		
		assertThat(evaluationDto.getAnswers(), equalTo(answers));
		assertThat(evaluationDto.getCategories(), equalTo(categories));
		assertThat(evaluationDto.getCreationDate(), equalTo(creationDate));
		assertThat(evaluationDto.getCurrentPage(), equalTo(currentPage));
		assertThat(evaluationDto.getEndTime(), equalTo(endTime));
		assertThat(evaluationDto.isError(), equalTo(error));
		assertThat(evaluationDto.getErrorMessage(), equalTo(errorMessage));
		assertThat(evaluationDto.getEvalMinutes(), equalTo(evalMinutes));
		assertThat(evaluationDto.getExpirationDate(), equalTo(expirationDate));
		assertThat(evaluationDto.isFinish(), equalTo(finish));
		assertThat(evaluationDto.getIdApplicant(), equalTo(idApplicant));
		assertThat(evaluationDto.getIdEvaluation(), equalTo(idEvaluation));
		assertThat(evaluationDto.getIdEvaluationsQuestions(), equalTo(idEvaluationsQuestions));
		assertThat(evaluationDto.getIdExam(), equalTo(idExam));
		assertThat(evaluationDto.getIdGenerator(), equalTo(idGenerator));
		assertThat(evaluationDto.getName(), equalTo(name));
		assertThat(evaluationDto.getPaginador(), equalTo(paginador));
		assertThat(evaluationDto.getPassPercent(), equalTo(passPercent));
		assertThat(evaluationDto.getQuestionList(), equalTo(questionList));
		assertThat(evaluationDto.getQuestions(), equalTo(questions));
		assertThat(evaluationDto.getQuestionsPerPage(), equalTo(questionsPerPage));
		assertThat(evaluationDto.getStartTime(), equalTo(startTime));
		assertThat(evaluationDto.getStatus(), equalTo(status));
		assertThat(evaluationDto.getStep(), equalTo(step));
		assertThat(evaluationDto.isTimeout(), equalTo(timeout));
		
		
	}
	
	@Test
	public void examDtoTest(){
		ExamDto examDto = new ExamDto();
		Set<Category> categories = new HashSet<>();
		List<ComboDto> comboCategories = new ArrayList<>();
		String direction = "direction";
		int examMinutes = 1;
		List<Exam> exams = new ArrayList<>();
		Long idExam = 1L;
		List<Long> idsCategories = new ArrayList<>();
		String name = "name";
		int page = 1;
		BigDecimal passPercent = BigDecimal.ONE;
		int questions = 1;
		int size = 1;
		Long totalItems = 1L;
		int totalPages = 1;
		
		examDto.setCategories(categories);
		examDto.setComboCategories(comboCategories);
		examDto.setDirection(direction);
		examDto.setExamMinutes(examMinutes);
		examDto.setExams(exams);
		examDto.setIdExam(idExam);
		examDto.setIdsCategories(idsCategories);
		examDto.setName(name);
		examDto.setPage(page);
		examDto.setPassPercent(passPercent);
		examDto.setQuestions(questions);
		examDto.setSize(size);
		examDto.setTotalItems(totalItems);
		examDto.setTotalPages(totalPages);
		
		assertThat(examDto.getCategories(), equalTo(categories));
		assertThat(examDto.getComboCategories(), equalTo(comboCategories));
		assertThat(examDto.getDirection(), equalTo(direction));
		assertThat(examDto.getExamMinutes(), equalTo(examMinutes));
		assertThat(examDto.getExams(), equalTo(exams));
		assertThat(examDto.getIdExam(), equalTo(idExam));
		assertThat(examDto.getIdsCategories(), equalTo(idsCategories));
		assertThat(examDto.getName(), equalTo(name));
		assertThat(examDto.getPage(), equalTo(page));
		assertThat(examDto.getPassPercent(), equalTo(passPercent));
		assertThat(examDto.getQuestions(), equalTo(questions));
		assertThat(examDto.getSize(), equalTo(size));
		assertThat(examDto.getTotalItems(), equalTo(totalItems));
		assertThat(examDto.getTotalPages(), equalTo(totalPages));
	}
	
	@Test
	public void generateEvalDtoTest(){
		GenerateEvalDto generateEvalDto = new GenerateEvalDto();
		String email = "email";
		int evalMinutes = 1;
		Long idExam = 1L;
		List<Long> idsCategories = new ArrayList<>();
		String name = "name";
		String nameApplicant = "nameApplicant";
		Integer passPercent = 1;
		Integer questions = 1;
		SourceEvaluation source = SourceEvaluation.CATEGORIES;
		int step = 1;
		
		generateEvalDto.setEmail(email);
		generateEvalDto.setEvalMinutes(evalMinutes);
		generateEvalDto.setIdExam(idExam);
		generateEvalDto.setIdsCategories(idsCategories);
		generateEvalDto.setName(name);
		generateEvalDto.setNameApplicant(nameApplicant);
		generateEvalDto.setPassPercent(passPercent);
		generateEvalDto.setQuestions(questions);
		generateEvalDto.setSource(source);
		generateEvalDto.setStep(step);
		
		assertThat(generateEvalDto.getEmail(), equalTo(email));
		assertThat(generateEvalDto.getEvalMinutes(), equalTo(evalMinutes));
		assertThat(generateEvalDto.getIdExam(), equalTo(idExam));
		assertThat(generateEvalDto.getIdsCategories(), equalTo(idsCategories));
		assertThat(generateEvalDto.getName(), equalTo(name));
		assertThat(generateEvalDto.getNameApplicant(), equalTo(nameApplicant));
		assertThat(generateEvalDto.getPassPercent(), equalTo(passPercent));
		assertThat(generateEvalDto.getQuestions(), equalTo(questions));
		assertThat(generateEvalDto.getSource(), equalTo(source));
		assertThat(generateEvalDto.getStep(), equalTo(step));
	}
	
	@Test
	public void paginadorDtoTest(){
		PaginadorDto paginadorDto = new PaginadorDto();
		String direction = "direction";
		int page = 1;
		int size = 1;
		Long totalItems = 1L;
		int totalPages = 1;
		
		paginadorDto.setDirection(direction);
		paginadorDto.setPage(page);
		paginadorDto.setSize(size);
		paginadorDto.setTotalItems(totalItems);
		paginadorDto.setTotalPages(totalPages);
		
		assertThat(paginadorDto.getDirection(), equalTo(direction));
		assertThat(paginadorDto.getPage(), equalTo(page));
		assertThat(paginadorDto.getSize(), equalTo(size));
		assertThat(paginadorDto.getTotalItems(), equalTo(totalItems));
		assertThat(paginadorDto.getTotalPages(), equalTo(totalPages));
	}
	
	@Test
	public void questionDtoTest(){
		QuestionDto questionDto = new QuestionDto();
		Set<Answer> answers = new HashSet<>();
		List<Integer> answersCorrects = new ArrayList<>();
		List<String> answersTexts = new ArrayList<>();
		String body = "body";
		Set<Category> categories = new HashSet<>();
		List<String> categoriesDescriptions = new ArrayList<>();
		List<QuestionStatus> comboStatus = new ArrayList<>();
		Date creationDate = new Date();
		String direction = "direction";
		Long idCreator = 1L;
		Long idQuestion = 1L;
		int page = 1;
		List<Question> questions = new ArrayList<>();
		String searchCategory = "searchCategory";
		QuestionStatus searchStatus = QuestionStatus.ACTIVA;
		int size = 1;
		QuestionStatus status = QuestionStatus.ACTIVA;
		String text = "text";
		Long totalItems = 1L;
		int totalPages = 1;
		
		questionDto.setAnswers(answers);
		questionDto.setAnswersCorrects(answersCorrects);
		questionDto.setAnswersTexts(answersTexts);
		questionDto.setBody(body);
		questionDto.setCategories(categories);
		questionDto.setCategoriesDescriptions(categoriesDescriptions);
		questionDto.setComboStatus(comboStatus);
		questionDto.setCreationDate(creationDate);
		questionDto.setDirection(direction);
		questionDto.setIdCreator(idCreator);
		questionDto.setIdQuestion(idQuestion);
		questionDto.setPage(page);
		questionDto.setQuestions(questions);
		questionDto.setSearchCategory(searchCategory);
		questionDto.setSearchStatus(searchStatus);
		questionDto.setSize(size);
		questionDto.setStatus(status);
		questionDto.setText(text);
		questionDto.setTotalItems(totalItems);
		questionDto.setTotalPages(totalPages);
		
		assertThat(questionDto.getAnswers(), equalTo(answers));
		assertThat(questionDto.getAnswersCorrects(), equalTo(answersCorrects));
		assertThat(questionDto.getAnswersTexts(), equalTo(answersTexts));
		assertThat(questionDto.getBody(), equalTo(body));
		assertThat(questionDto.getCategories(), equalTo(categories));
		assertThat(questionDto.getCategoriesDescriptions(), equalTo(categoriesDescriptions));
		assertThat(questionDto.getComboStatus(), equalTo(comboStatus));
		assertThat(questionDto.getCreationDate(), equalTo(creationDate));
		assertThat(questionDto.getDirection(), equalTo(direction));
		assertThat(questionDto.getIdCreator(), equalTo(idCreator));
		assertThat(questionDto.getIdQuestion(), equalTo(idQuestion));
		assertThat(questionDto.getPage(), equalTo(page));
		assertThat(questionDto.getQuestions(), equalTo(questions));
		assertThat(questionDto.getSearchCategory(), equalTo(searchCategory));
		assertThat(questionDto.getSearchStatus(), equalTo(searchStatus));
		assertThat(questionDto.getSize(), equalTo(size));
		assertThat(questionDto.getStatus(), equalTo(status));
		assertThat(questionDto.getText(), equalTo(text));
		assertThat(questionDto.getTotalItems(), equalTo(totalItems));
		assertThat(questionDto.getTotalPages(), equalTo(totalPages));
	}
	
	@Test
	public void userTest(){
		User user = new User();
		boolean active = true;
		Set<Authority> authorities = new HashSet<>();
		String email = "email";
		Date expirationDate = new Date();
		Long idUser = 1L;
		boolean locked = true;
		String name = "name";
		String password = "password";
		String username = "username";
		
		user.setActive(active);
		user.setAuthorities(authorities);
		user.setEmail(email);
		user.setExpirationDate(expirationDate);
		user.setIdUser(idUser);
		user.setLocked(locked);
		user.setName(name);
		user.setPassword(password);
		user.setUsername(username);
		
		assertThat(user.isActive(), equalTo(active));
		assertThat(user.getAuthorities(), equalTo(authorities));
		assertThat(user.getEmail(), equalTo(email));
		assertThat(user.getExpirationDate(), equalTo(expirationDate));
		assertThat(user.getIdUser(), equalTo(idUser));
		assertThat(user.isLocked(), equalTo(locked));
		assertThat(user.getName(), equalTo(name));
		assertThat(user.getPassword(), equalTo(password));
		assertThat(user.getUsername(), equalTo(username));
	}
	
	@Test
	public void viewEvalDtoTest(){
		ViewEvalDto viewEvalDto = new ViewEvalDto();
		String direction = "direction";
		List<ViewEvaluation> evaluations = new ArrayList<>();
		String name = "name";
		int page = 1;
		String propertySort = "propertySort";
		String searchManager = "searchManager";
		String searchResultado = "searchResultado";
		EvaluationStatus searchStatus = EvaluationStatus.AGOTADA;
		int size = 1;
		Long totalItems = 1L;
		int totalPages = 1;
		
		viewEvalDto.setDirection(direction);
		viewEvalDto.setEvaluations(evaluations);
		viewEvalDto.setName(name);
		viewEvalDto.setPage(page);
		viewEvalDto.setPropertySort(propertySort);
		viewEvalDto.setSearchManager(searchManager);
		viewEvalDto.setSearchResultado(searchResultado);
		viewEvalDto.setSearchStatus(searchStatus);
		viewEvalDto.setSize(size);
		viewEvalDto.setTotalItems(totalItems);
		viewEvalDto.setTotalPages(totalPages);
		
		assertThat(viewEvalDto.getDirection(), equalTo(direction));
		assertThat(viewEvalDto.getEvaluations(), equalTo(evaluations));
		assertThat(viewEvalDto.getName(), equalTo(name));
		assertThat(viewEvalDto.getPage(), equalTo(page));
		assertThat(viewEvalDto.getPropertySort(), equalTo(propertySort));
		assertThat(viewEvalDto.getSearchManager(), equalTo(searchManager));
		assertThat(viewEvalDto.getSearchResultado(), equalTo(searchResultado));
		assertThat(viewEvalDto.getSearchStatus(), equalTo(searchStatus));
		assertThat(viewEvalDto.getSize(), equalTo(size));
		assertThat(viewEvalDto.getTotalItems(), equalTo(totalItems));
		assertThat(viewEvalDto.getTotalPages(), equalTo(totalPages));
	}
	
}
