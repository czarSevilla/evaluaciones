package czar.evaluaciones.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import czar.evaluaciones.entities.Category;
import czar.evaluaciones.entities.EvaluationQuestion;
import czar.evaluaciones.enums.EvaluationStatus;

public class EvaluationDto implements Serializable {


    private static final long serialVersionUID = 1L;
    private Long idEvaluation;    
    private boolean error;    
    private String errorMessage;    
    private Long idExam;
    private Long idGenerator;
    private Long idApplicant;
    private Integer questions;
    private BigDecimal passPercent;
    private Date creationDate;
    private Date expirationDate;
    private EvaluationStatus status;
    private String name;
    private Set<Category> categories;
    private int evalMinutes;
    private Integer questionsPerPage;
    private Integer currentPage;
    private int step;
    private Date startTime;
    private Date endTime;
    private List<String> answers;
    private Set<Long> idEvaluationsQuestions;
    private boolean finish;
    private boolean timeout;
    private List<EvaluationQuestion> questionList;
    private PaginadorDto paginador;
    
	public EvaluationDto() {
	 // Constructor default
    }

    public Long getIdEvaluation() {
        return idEvaluation;
    }

    public void setIdEvaluation(Long idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getIdExam() {
        return idExam;
    }

    public void setIdExam(Long idExam) {
        this.idExam = idExam;
    }

    public Long getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(Long idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Long getIdApplicant() {
        return idApplicant;
    }

    public void setIdApplicant(Long idApplicant) {
        this.idApplicant = idApplicant;
    }

    public Integer getQuestions() {
        return questions;
    }

    public void setQuestions(Integer questions) {
        this.questions = questions;
    }

    public BigDecimal getPassPercent() {
        return passPercent;
    }

    public void setPassPercent(BigDecimal passPercent) {
        this.passPercent = passPercent;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public EvaluationStatus getStatus() {
        return status;
    }

    public void setStatus(EvaluationStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public int getEvalMinutes() {
        return evalMinutes;
    }

    public void setEvalMinutes(int evalMinutes) {
        this.evalMinutes = evalMinutes;
    }

    public Integer getQuestionsPerPage() {
        return questionsPerPage;
    }

    public void setQuestionsPerPage(Integer questionsPerPage) {
        this.questionsPerPage = questionsPerPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
    
    public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}   
	
	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

    public Set<Long> getIdEvaluationsQuestions() {
        return idEvaluationsQuestions;
    }

    public void setIdEvaluationsQuestions(Set<Long> idEvaluationsQuestions) {
        this.idEvaluationsQuestions = idEvaluationsQuestions;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

	public boolean isTimeout() {
		return timeout;
	}

	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}

	public List<EvaluationQuestion> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<EvaluationQuestion> questionList) {
		this.questionList = questionList;
	}

	public PaginadorDto getPaginador() {
		return paginador;
	}

	public void setPaginador(PaginadorDto paginador) {
		this.paginador = paginador;
	}
}
