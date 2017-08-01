package czar.evaluaciones.entities.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import czar.evaluaciones.entities.Answer;
import czar.evaluaciones.entities.Authority;
import czar.evaluaciones.entities.Category;
import czar.evaluaciones.entities.Configuration;
import czar.evaluaciones.entities.Evaluation;
import czar.evaluaciones.entities.EvaluationAnswer;
import czar.evaluaciones.entities.EvaluationQuestion;
import czar.evaluaciones.entities.Exam;
import czar.evaluaciones.entities.Notification;
import czar.evaluaciones.entities.Question;
import czar.evaluaciones.entities.User;
import czar.evaluaciones.entities.ViewEvaluation;
import czar.evaluaciones.enums.EvaluationStatus;
import czar.evaluaciones.enums.NotificationStatus;
import czar.evaluaciones.enums.NotificationType;
import czar.evaluaciones.enums.QuestionStatus;
import czar.evaluaciones.utils.DateUtils;

public class EntitiesTest {

	@Test
	public void userTest() {
		User user = new User();
		boolean active = true;
		Set<Authority> authorities = new HashSet<>();
		Date expirationDate = new Date();
		String email = "user@dominio.com";
		boolean locked = true;
		Long idUser=1L;
		String name = "Nombre de Prueba";
		String password = "password";
		String username = "username";
		
		user.setActive(active);
		user.setAuthorities(authorities);
		user.setExpirationDate(expirationDate);
		user.setEmail(email);
		user.setIdUser(idUser);
		user.setLocked(locked);
		user.setName(name);
		user.setPassword(password);
		user.setUsername(username);
		
		assertThat(user.isActive(), equalTo(active));
		assertThat(user.getAuthorities(), equalTo(authorities));
		assertThat(user.getExpirationDate(), equalTo(expirationDate));
		assertThat(user.getEmail(), equalTo(email));
		assertThat(user.getIdUser(), equalTo(idUser));
		assertThat(user.isLocked(), equalTo(locked));
		assertThat(user.getName(), equalTo(name));
		assertThat(user.getPassword(), equalTo(password));
		assertThat(user.getUsername(), equalTo(username));
		
		user.setExpirationDate(DateUtils.addDays(new Date(), 2));
		user.setLocked(false);
		assertTrue(user.isAccountNonExpired());
		assertTrue(user.isAccountNonLocked());
		assertTrue(user.isCredentialsNonExpired());
		assertTrue(user.isEnabled());
		user.setLocked(true);
		assertFalse(user.isCredentialsNonExpired());
		assertFalse(user.isAccountNonLocked());
		assertFalse(user.isEnabled());
		user.setExpirationDate(new Date());
		assertFalse(user.isAccountNonExpired());
		assertFalse(user.isEnabled());
		user.setExpirationDate(null);
		assertTrue(user.isAccountNonExpired());
	}
	
	@Test
	public void answerTest(){
		Answer answer = new Answer();
		boolean correct = true;
		Long idAnswer = 1L;
		Question question = new Question();  
		String text = "cadena"; 
		
		answer.setCorrect(correct);
		answer.setIdAnswer(idAnswer);
		answer.setQuestion(question);
		answer.setText(text);
	
		assertThat(answer.isCorrect(), equalTo(correct));
		assertThat(answer.getIdAnswer(), equalTo(idAnswer));
		assertThat(answer.getQuestion(), equalTo(question));
		assertThat(answer.getText(), equalTo(text));
	}
	
	@Test
	public void authorityTest(){
		Authority authority = new Authority();
		String authorit = "cadena";
		Long idAuthority = 1L;
		User user = new User();
		
		authority.setAuthority(authorit);
		authority.setIdAuthority(idAuthority);
		authority.setUser(user);
		
		assertThat(authority.getAuthority(), equalTo(authorit));
		assertThat(authority.getIdAuthority(), equalTo(idAuthority));
		assertThat(authority.getUser(), equalTo(user));
		assertThat(authority.toString(), equalTo(authorit));
	}
	
	@Test
	public void categoryTest(){
		Category category = new Category();
		Category categoryN = new Category(1L);
		String description =  "cadena";
		Long idCategory = 1L;
		
		category.setDescription(description);
		category.setIdCategory(idCategory);
		
		
		assertThat(category.getDescription(), equalTo(description));
		assertThat(category.getIdCategory(), equalTo(idCategory));
		assertThat(categoryN.getIdCategory(), equalTo(1L));
	}
	
	@Test
	public void configurationTest(){
		Configuration configuration = new Configuration();
		Long idConfiguration = 1L;
		String key = "key";
		String value = "value";
		
		configuration.setIdConfiguration(idConfiguration);
		configuration.setKey(key);
		configuration.setValue(value);
		
		assertThat(configuration.getIdConfiguration(), equalTo(idConfiguration));
		assertThat(configuration.getKey(), equalTo(key));
		assertThat(configuration.getValue(), equalTo(value));
	}
	
	@Test
	public void evaluationTest(){
		Evaluation evaluation = new Evaluation();
		Category category = new Category();
		Set<Category> categories = new HashSet<>();
		Date creationDate = new Date();
		Integer currentPage = 1;
		Date endTime = new Date();
		int evalMinutes = 1;
		Date expirationDate = new Date();
		Long idApplicant = 1L;
		Long idEvaluation = 1L;
		Long idExam = 1L;
		Long idGenerator = 1L; 
		String name = "name";
		BigDecimal passPercent = BigDecimal.ZERO;
		Integer questions = 1;
		Integer questionsPerPage = 1;
		BigDecimal result = BigDecimal.ZERO;
		Date startTime = new Date();
		EvaluationStatus status = EvaluationStatus.AGOTADA;
		
		evaluation.setCategories(categories);
		evaluation.setCreationDate(creationDate);
		evaluation.setCurrentPage(currentPage);
		evaluation.setEndTime(endTime);
		evaluation.setEvalMinutes(evalMinutes);
		evaluation.setExpirationDate(expirationDate);
		evaluation.setIdApplicant(idApplicant);
		evaluation.setIdEvaluation(idEvaluation);
		evaluation.setIdExam(idExam);
		evaluation.setIdGenerator(idGenerator);
		evaluation.setName(name);
		evaluation.setPassPercent(passPercent);
		evaluation.setQuestions(questions);
		evaluation.setQuestionsPerPage(questionsPerPage);
		evaluation.setResult(result);
		evaluation.setStartTime(startTime);
		evaluation.setStatus(status);
		evaluation.addCategory(category);
		
		assertThat(evaluation.getCategories(), equalTo(categories));
		assertThat(evaluation.getCreationDate(), equalTo(creationDate));
		assertThat(evaluation.getCurrentPage(), equalTo(currentPage));
		assertThat(evaluation.getEndTime(), equalTo(endTime));
		assertThat(evaluation.getEvalMinutes(), equalTo(evalMinutes));
		assertThat(evaluation.getExpirationDate(), equalTo(expirationDate));
		assertThat(evaluation.getIdApplicant(), equalTo(idApplicant));
		assertThat(evaluation.getIdEvaluation(), equalTo(idEvaluation));
		assertThat(evaluation.getIdExam(), equalTo(idExam));
		assertThat(evaluation.getIdGenerator(), equalTo(idGenerator));
		assertThat(evaluation.getName(), equalTo(name));
		assertThat(evaluation.getPassPercent(), equalTo(passPercent));
		assertThat(evaluation.getQuestions(), equalTo(questions));
		assertThat(evaluation.getQuestionsPerPage(), equalTo(questionsPerPage));
		assertThat(evaluation.getResult(), equalTo(result));
		assertThat(evaluation.getStartTime(), equalTo(startTime));
		assertThat(evaluation.getStatus(), equalTo(status));
		assertTrue(evaluation.getCategories().contains(category));
	}
	
	@Test
	public void evaluationAnswerTest(){
	EvaluationAnswer evaluationanswer = new EvaluationAnswer();
	EvaluationAnswer evaluationAnswer = new EvaluationAnswer(1L, 1L);
	boolean correct = true;
	Long idAnswer =  1L;
	Long idEvaluationAnswer = 1L;
	Long idEvaluationQuestion = 1L;
	
	evaluationanswer.setCorrect(correct);
	evaluationanswer.setIdAnswer(idAnswer);
	evaluationanswer.setIdEvaluationAnswer(idEvaluationAnswer);
	evaluationanswer.setIdEvaluationQuestion(idEvaluationQuestion);
	
	assertThat(evaluationanswer.isCorrect(), equalTo(correct));
	assertThat(evaluationanswer.getIdAnswer(), equalTo(idAnswer));
	assertThat(evaluationanswer.getIdEvaluationAnswer(), equalTo(idEvaluationAnswer));
	assertThat(evaluationanswer.getIdEvaluationQuestion(), equalTo(idEvaluationQuestion));
	assertThat(evaluationAnswer.getIdEvaluationQuestion(), equalTo(1L));
	assertThat(evaluationAnswer.getIdAnswer(), equalTo(1L));
	}
	
	@Test
	public void evaluationQuestionTest(){
		EvaluationQuestion evaluationQuestion = new EvaluationQuestion();
		Long idEvaluation = 1L;
		Long idEvaluationQuestion = 1L;
		Question question = new Question();
		
		evaluationQuestion.setIdEvaluation(idEvaluation);
		evaluationQuestion.setIdEvaluationQuestion(idEvaluationQuestion);
		evaluationQuestion.setQuestion(question);
		
		assertThat(evaluationQuestion.getIdEvaluation(), equalTo(idEvaluation));
		assertThat(evaluationQuestion.getIdEvaluationQuestion(), equalTo(idEvaluationQuestion));
		assertThat(evaluationQuestion.getQuestion(), equalTo(question));
	}
	
	@Test
	public void examTest(){
		Exam exam = new Exam();
		Category category = new Category();
		Set<Category> categories = new HashSet<>();
		int examMinutes = 1;
		Long idExam = 1L;
		String name = "name";
		BigDecimal passPercent = BigDecimal.ZERO;
		int questions = 1;
		
		exam.setCategories(categories);
		exam.setExamMinutes(examMinutes);
		exam.setIdExam(idExam);
		exam.setName(name);
		exam.setPassPercent(passPercent);
		exam.setQuestions(questions);
		exam.addCategory(category);
		
		assertThat(exam.getCategories(), equalTo(categories));
		assertThat(exam.getExamMinutes(), equalTo(examMinutes));
		assertThat(exam.getIdExam(),equalTo(idExam));
		assertThat(exam.getName(), equalTo(name));
		assertThat(exam.getPassPercent(), equalTo(passPercent));
		assertThat(exam.getQuestions(), equalTo(questions));
		assertTrue(exam.getCategories().contains(category));
	}
	
	@Test
	public void notificationTest(){
		Notification notification = new Notification();
		String emailApplicant = "emailApplicant";
		Date expirationDate = new Date();
		Long idNotificacion = 1L;
		User manager = new User();
		String nameApplicant = "nameApplicant";
		String passwordApplicant = "passwordApplicant";
		NotificationStatus status = NotificationStatus.ENVIADA;
		NotificationType type = NotificationType.EVALUACION;
		
		notification.setEmailApplicant(emailApplicant);
		notification.setExpirationDate(expirationDate);
		notification.setIdNotificacion(idNotificacion);
		notification.setManager(manager); 
		notification.setNameApplicant(nameApplicant);
		notification.setPasswordApplicant(passwordApplicant);
		notification.setStatus(status);
		notification.setType(type);
		
		assertThat(notification.getEmailApplicant(), equalTo(emailApplicant));
		assertThat(notification.getExpirationDate(), equalTo(expirationDate));
		assertThat(notification.getIdNotificacion(), equalTo(idNotificacion));
		assertThat(notification.getManager(), equalTo(manager));
		assertThat(notification.getNameApplicant(), equalTo(nameApplicant));
		assertThat(notification.getPasswordApplicant(), equalTo(passwordApplicant));
		assertThat(notification.getStatus(), equalTo(status));
		assertThat(notification.getType(), equalTo(type));
	}
	
	@Test
	public void questionTest(){
		Question question = new Question();
		Category category = new Category();
		Answer answer = new Answer();
		Set<Answer> answers = new HashSet<>();
		String body = "body";
		Set<Category> categories = new HashSet<>();
		Date creationDate = new Date();
		Long idCreator = 1L;
		Long idQuestion =  1L;
		QuestionStatus status = QuestionStatus.ACTIVA;
		String text = "text";
		
		
		question.setAnswers(answers);
		question.setBody(body);
		question.setCategories(categories);
		question.setCreationDate(creationDate);
		question.setIdCreator(idCreator);
		question.setIdQuestion(idQuestion);
		question.setStatus(status);
		question.setText(text);
		question.addCategory(category);
		question.addAnswer(answer);
		
		assertThat(question.getAnswers(), equalTo(answers));
		assertThat(question.getBody(), equalTo(body));
		assertThat(question.getCategories(), equalTo(categories));
		assertThat(question.getCreationDate(), equalTo(creationDate));
		assertThat(question.getIdCreator(), equalTo(idCreator));
		assertThat(question.getIdQuestion(), equalTo(idQuestion));
		assertThat(question.getStatus(), equalTo(status));
		assertThat(question.getText(), equalTo(text));
		assertTrue(question.getCategories().contains(category));
		assertTrue(question.getAnswers().contains(answer));
	}
	
	@Test
	public void viewEvaluation(){
		ViewEvaluation viewEvaluation = new ViewEvaluation();
		String applicant = "applicant";
		String approvalResult = "approvalResult";
		Date creationDate = new Date();
		String evalTime = "evalTime";
		Date expirationDate = new Date();
		Long idEvaluation = 1L;
		String manager = "manager";
		String name = "name";
		BigDecimal passPercent = BigDecimal.ONE;
		Integer questions = 1;
		BigDecimal result = BigDecimal.ONE;
		EvaluationStatus status = EvaluationStatus.AGOTADA;
		
		viewEvaluation.setApplicant(applicant);
		viewEvaluation.setApprovalResult(approvalResult);
		viewEvaluation.setCreationDate(creationDate);
		viewEvaluation.setEvalTime(evalTime);
		viewEvaluation.setExpirationDate(expirationDate);
		viewEvaluation.setIdEvaluation(idEvaluation);
		viewEvaluation.setManager(manager);
		viewEvaluation.setName(name);
		viewEvaluation.setPassPercent(passPercent);
		viewEvaluation.setQuestions(questions);
		viewEvaluation.setResult(result);
		viewEvaluation.setStatus(status);
		
		assertThat(viewEvaluation.getApplicant(), equalTo(applicant));
		assertThat(viewEvaluation.getApprovalResult(), equalTo(approvalResult));
		assertThat(viewEvaluation.getCreationDate(), equalTo(creationDate));
		assertThat(viewEvaluation.getEvalTime(), equalTo(evalTime));
		assertThat(viewEvaluation.getExpirationDate(), equalTo(expirationDate));
		assertThat(viewEvaluation.getIdEvaluation(), equalTo(idEvaluation));
		assertThat(viewEvaluation.getManager(), equalTo(manager));
		assertThat(viewEvaluation.getName(), equalTo(name));
		assertThat(viewEvaluation.getPassPercent(), equalTo(passPercent));
		assertThat(viewEvaluation.getQuestions(), equalTo(questions));
		assertThat(viewEvaluation.getResult(), equalTo(result));
		assertThat(viewEvaluation.getStatus(), equalTo(status));
	}
}
