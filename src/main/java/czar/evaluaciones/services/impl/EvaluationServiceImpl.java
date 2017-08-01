package czar.evaluaciones.services.impl;

import static czar.evaluaciones.enums.Config.EXPIRATION_DAY;
import static czar.evaluaciones.enums.Config.QUESTIONS_X_PAGE;
import static czar.evaluaciones.enums.Config.ROWS_X_PAGE;
import static czar.evaluaciones.enums.EvaluationStatus.AGOTADA;
import static czar.evaluaciones.enums.EvaluationStatus.APLICADA;
import static czar.evaluaciones.enums.EvaluationStatus.EJECUCION;
import static czar.evaluaciones.enums.EvaluationStatus.PENDIENTE;
import static czar.evaluaciones.enums.QuestionStatus.ACTIVA;
import static czar.evaluaciones.enums.SourceEvaluation.CATEGORIES;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher.of;
import static org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;

import czar.evaluaciones.dtos.EvaluationDto;
import czar.evaluaciones.dtos.GenerateEvalDto;
import czar.evaluaciones.dtos.PaginadorDto;
import czar.evaluaciones.dtos.ViewEvalDto;
import czar.evaluaciones.entities.Category;
import czar.evaluaciones.entities.Evaluation;
import czar.evaluaciones.entities.EvaluationAnswer;
import czar.evaluaciones.entities.EvaluationQuestion;
import czar.evaluaciones.entities.Exam;
import czar.evaluaciones.entities.Notification;
import czar.evaluaciones.entities.Question;
import czar.evaluaciones.entities.User;
import czar.evaluaciones.entities.ViewEvaluation;
import czar.evaluaciones.enums.NotificationStatus;
import czar.evaluaciones.enums.NotificationType;
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
import czar.evaluaciones.utils.DateUtils;
import czar.evaluaciones.utils.SecurityUtils;

@Service("evaluationService")
public class EvaluationServiceImpl implements EvaluationService {
    
    @Autowired
    private ExamRepository examRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ConfigurationRepository configurationRepository;
    
    @Autowired
    private EvaluationRepository evaluationRepository;
    
    @Autowired
    private EvaluationQuestionRepository evaluationQuestionRepository;
    
    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private EvaluationAnswerRepository evaluationAnswerRepository;
    
    @Autowired
    private EvaluationAnswerCustomRepository evaluationAnswerCustomRepository;
    
    @Autowired
    private ViewEvaluationRepository viewEvaluationRepository;

    @Override
    @Transactional
    public void generateEvaluation(GenerateEvalDto generarEvalDto, Long idManager) {

        List<Long> categories = null;
        int questions = 0;
        String name = null;
        String email = generarEvalDto.getEmail();
        int evalMinutes = 0;
        int expirationDays = Integer.parseInt(configurationRepository.findByKey(EXPIRATION_DAY.toString()).getValue());
        int questionsPerPage = Integer.parseInt(configurationRepository.findByKey(QUESTIONS_X_PAGE.toString()).getValue());
        Date date = DateUtils.addDays(new Date(), expirationDays); 
        BigDecimal percent = null;
        
        if (generarEvalDto.getSource() == CATEGORIES) {
            categories = generarEvalDto.getIdsCategories();
            questions = generarEvalDto.getQuestions();
            percent = new BigDecimal(String.valueOf(generarEvalDto.getPassPercent())).multiply(new BigDecimal("0.01"));
            name = generarEvalDto.getName();
            evalMinutes = generarEvalDto.getEvalMinutes();
        } else {
            Exam exam = examRepository.findOne(generarEvalDto.getIdExam()); 
            Set<Category> cats = exam.getCategories();
            categories = cats.stream().map(cat -> cat.getIdCategory()).collect(Collectors.toList());
            questions = exam.getQuestions();
            percent = exam.getPassPercent();
            name = exam.getName();
            evalMinutes = exam.getExamMinutes();
        }

        List<Long> idsQuestions = questionRepository.findIdsQuestionsByIdsCategories(ACTIVA, categories);
        int totalQuestions = idsQuestions.size();
        if (totalQuestions < questions) {
            questions = totalQuestions;
        }
        List<Integer> idxQuestions = new ArrayList<>(questions); 
        ThreadLocalRandom.current().ints(0, totalQuestions).distinct().limit(questions).forEach(idxQuestions::add);
        List<Long> evalIdsQuestions = idxQuestions.stream().map(idx -> idsQuestions.get(idx)).collect(Collectors.toList());
        
        String plain = "";        
        
        User user = userRepository.findByUsername(email);
        if (user == null) {
            user = new User();
            user.addAuthority("ROLE_USER");            
            user.setEmail(email);    
            user.setUsername(email);
            user.setName(generarEvalDto.getNameApplicant());
            plain = SecurityUtils.generateRandomPassword();
            user.setPassword(passwordEncoder.encode(plain));        
        }    
        user.setActive(true);
        user.setExpirationDate(date);
        
        userRepository.save(user);
        
        Evaluation evaluation = new Evaluation();
        evaluation.setIdExam(generarEvalDto.getIdExam());
        evaluation.setName(name);
        evaluation.setIdGenerator(idManager);
        evaluation.setIdApplicant(user.getIdUser());
        evaluation.setQuestions(questions);
        evaluation.setPassPercent(percent);
        evaluation.setCreationDate(new Date());
        evaluation.setExpirationDate(date);
        evaluation.setStatus(PENDIENTE);
        evaluation.setEvalMinutes(evalMinutes);
        evaluation.setQuestionsPerPage(questionsPerPage);
        for (Long idCategory : categories) {
            Category category = new Category();
            category.setIdCategory(idCategory);
            evaluation.addCategory(category);
        }
        evaluationRepository.save(evaluation);
        
        for (Long idQuestion : evalIdsQuestions) {
            EvaluationQuestion eq = new EvaluationQuestion();
            eq.setIdEvaluation(evaluation.getIdEvaluation());
            Question q = new Question();
            q.setIdQuestion(idQuestion);
            eq.setQuestion(q);
            evaluationQuestionRepository.save(eq);
        }
        
        Notification notification = new Notification();
        notification.setNameApplicant(generarEvalDto.getNameApplicant());
        notification.setEmailApplicant(generarEvalDto.getEmail());
        notification.setPasswordApplicant(plain);
        User manager = new User();
        manager.setIdUser(idManager);
        notification.setManager(manager);
        notification.setStatus(NotificationStatus.REGISTRADA);
        notification.setExpirationDate(date);
        notification.setType(NotificationType.EVALUACION);
        notificationRepository.save(notification);
    }

    @Override
    public List<Evaluation> findPendingEvaluationsByIdUser(Long idUser) {
        return evaluationRepository.findAllPending(idUser, APLICADA, AGOTADA, new Date());
    }

    @Override
    @Transactional
    public EvaluationDto loadEvaluation(Long idEvaluation, Long idApplicant, EvaluationDto prev) {
        EvaluationDto eval = new EvaluationDto();
        
        Evaluation evaluation = evaluationRepository.findOne(idEvaluation);
        
        if (evaluation == null) {
            eval.setError(true);
            eval.setErrorMessage("La evaluaci\u00F3n no se encontr\u00F3");
            return eval;
        }
        if (evaluation.getIdApplicant().compareTo(idApplicant) != 0) {
            eval.setError(true);
            eval.setErrorMessage("La evaluaci\u00F3n no corresponde al usuario");
            return eval;
        }
        
        if (prev.getStep() == 1 && evaluation.getStatus() == PENDIENTE) {
        	evaluation.setStatus(EJECUCION);
        	Date now = new Date();
        	Date finish = DateUtils.addMinutes(now, evaluation.getEvalMinutes());
        	evaluation.setStartTime(now);
        	evaluation.setEndTime(finish);
        	evaluation.setCurrentPage(0);
        	evaluationRepository.save(evaluation);
        }
        
        if (prev.getStep() == 2 || prev.getStep() == 3) {
            evaluationAnswerCustomRepository.deleteAnswers(prev.getIdEvaluationsQuestions());
            if (prev.getAnswers() != null && !prev.getAnswers().isEmpty()) {
                List<EvaluationAnswer> answers = new ArrayList<>();
            	for (String resps : prev.getAnswers()) {
            		String[] tokens = resps.split("_");
            		Long idEvaluationQuestion = Long.valueOf(tokens[1]);
            		Long idAnswer = Long.valueOf(tokens[2]);
            		answers.add(new EvaluationAnswer(idEvaluationQuestion, idAnswer));
            	}        	
            	evaluationAnswerRepository.save(answers);
            }
        	evaluationAnswerCustomRepository.updateAnswersCorrect(idEvaluation);
        	evaluation.setCurrentPage(prev.getCurrentPage());
        	evaluationRepository.save(evaluation);
        }
        
        if (prev.getStep() == 3) {
            BigDecimal result = evaluationAnswerCustomRepository.getResult(idEvaluation);
            if (prev.isTimeout()) {
            	evaluation.setStatus(AGOTADA);
            } else {
            	evaluation.setStatus(APLICADA);
            }
            evaluation.setEndTime(new Date());
            evaluation.setResult(result);
            evaluationRepository.save(evaluation);
            eval.setFinish(true);
            return eval;
        }
        
        BeanUtils.copyProperties(evaluation, eval);
        
        int page = evaluation.getCurrentPage();
        
        Pageable pageable = new PageRequest(page, eval.getQuestionsPerPage(), Direction.ASC, "idEvaluationQuestion");
        Page<EvaluationQuestion> pageEvalQ = evaluationQuestionRepository.findByIdEvaluation(idEvaluation, pageable);
        eval.setQuestionList(pageEvalQ.getContent());
        PaginadorDto paginador = new PaginadorDto();
        paginador.setDirection("ASC");
        paginador.setPage(pageEvalQ.getNumber());
        paginador.setSize(pageEvalQ.getSize());
        paginador.setTotalItems(pageEvalQ.getTotalElements());
        paginador.setTotalPages(pageEvalQ.getTotalPages());
        eval.setPaginador(paginador);
        
        if (pageEvalQ.hasContent()) {
            Set<Long> newIdEvalQuestion = new HashSet<>();
            for (EvaluationQuestion evalQ : pageEvalQ.getContent()) {
                newIdEvalQuestion.add(evalQ.getIdEvaluationQuestion());
            }
            List<String> currentAnswers = evaluationAnswerCustomRepository.loadAnswers(newIdEvalQuestion);
            eval.setAnswers(currentAnswers);
        }
        
        return eval;
    }

	@Override
	public ViewEvalDto findEvaluations(ViewEvalDto viewEvalDto) {
		int size = Integer.parseInt(configurationRepository.findByKey(ROWS_X_PAGE.toString()).getValue()); 
		if (viewEvalDto.getSize() > 0) {
			size = viewEvalDto.getSize();
		}
		Direction dir = Direction.DESC;
		if (viewEvalDto.getDirection() != null) {
			dir = Direction.fromString(viewEvalDto.getDirection());
		}
		String property = "idEvaluation";
		if (viewEvalDto.getPropertySort() != null) {
			property = viewEvalDto.getPropertySort();
		}
		Pageable pageable = new PageRequest(viewEvalDto.getPage(), size, dir, property);
		Page<ViewEvaluation> page = null;
		
			
		ViewEvaluation ve = new ViewEvaluation();
		
		ExampleMatcher matcher = ExampleMatcher.matching();
		
		if (!StringUtils.isNullOrEmpty(viewEvalDto.getName())) {
			matcher = matcher.withMatcher("applicant", of(CONTAINING));
			ve.setApplicant(viewEvalDto.getName());
		}
		if (viewEvalDto.getSearchStatus() != null) {
			ve.setStatus(viewEvalDto.getSearchStatus());
		}
		if (!StringUtils.isNullOrEmpty(viewEvalDto.getSearchResultado())) {
			ve.setApprovalResult(viewEvalDto.getSearchResultado());
		}
		if (!StringUtils.isNullOrEmpty(viewEvalDto.getSearchManager())) {
			ve.setManager(viewEvalDto.getSearchManager());
		}
		Example<ViewEvaluation> example = Example.of(ve, matcher);
		page = viewEvaluationRepository.findAll(example, pageable);
		
		ViewEvalDto result = new ViewEvalDto();
		result.setEvaluations(page.getContent());
		result.setPage(page.getNumber());
		result.setSize(page.getSize());
		result.setDirection(dir.toString());
		result.setPropertySort(property);
		result.setTotalPages(page.getTotalPages());
		result.setTotalItems(page.getTotalElements());
		result.setName(viewEvalDto.getName());
		result.setSearchManager(viewEvalDto.getSearchManager());
		result.setSearchResultado(viewEvalDto.getSearchResultado());
		result.setSearchStatus(viewEvalDto.getSearchStatus());
		return result;
	}

}
