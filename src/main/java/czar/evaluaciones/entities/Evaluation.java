package czar.evaluaciones.entities;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import czar.evaluaciones.enums.EvaluationStatus;

@Entity
@Table(name = "ev_evaluations")
public class Evaluation implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idEvaluation;
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
    private Date startTime;
    private Date endTime;
    private BigDecimal result;

    public Evaluation() {
        categories = new HashSet<>();
    }

    @Id
    @Column(name = "id_evaluation")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getIdEvaluation() {
        return idEvaluation;
    }

    public void setIdEvaluation(Long idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    @Column(name = "id_exam", nullable = true)
    public Long getIdExam() {
        return idExam;
    }

    public void setIdExam(Long idExam) {
        this.idExam = idExam;
    }

    @Column(name = "id_generator", nullable = false)
    public Long getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(Long idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Column(name = "id_applicant", nullable = false)
    public Long getIdApplicant() {
        return idApplicant;
    }

    public void setIdApplicant(Long idApplicant) {
        this.idApplicant = idApplicant;
    }

    @Column(name = "questions", nullable = false)
    public Integer getQuestions() {
        return questions;
    }

    public void setQuestions(Integer questions) {
        this.questions = questions;
    }

    @Column(name = "pass_percent", nullable = false)
    public BigDecimal getPassPercent() {
        return passPercent;
    }

    public void setPassPercent(BigDecimal passPercent) {
        this.passPercent = passPercent;
    }

    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "expiration_date", nullable = false)
    @Temporal(TemporalType.DATE)
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public EvaluationStatus getStatus() {
        return status;
    }

    public void setStatus(EvaluationStatus status) {
        this.status = status;
    }
    
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name = "eval_minutes", nullable = false)
    public int getEvalMinutes() {
        return evalMinutes;
    }

    public void setEvalMinutes(int evalMinutes) {
        this.evalMinutes = evalMinutes;
    }
    
    @Column(name = "questions_x_page", nullable = false)
    public Integer getQuestionsPerPage() {
        return questionsPerPage;
    }

    public void setQuestionsPerPage(Integer questionsPerPage) {
        this.questionsPerPage = questionsPerPage;
    }

    @Column(name = "current_page", nullable = true)
    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    @Column(name = "start_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Column
    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ev_evaluations_categories", joinColumns = @JoinColumn(name = "id_evaluation"), inverseJoinColumns = @JoinColumn(name = "id_category"))
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
    
    @Transient
    public void addCategory(Category category) {
        this.categories.add(category);
    }
}
