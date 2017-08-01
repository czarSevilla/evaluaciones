package czar.evaluaciones.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import czar.evaluaciones.enums.EvaluationStatus;

@Entity
@Table(name = "ev_v_evaluations")
public class ViewEvaluation implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idEvaluation;
	private Integer questions;
	private BigDecimal passPercent;
	private Date creationDate;
	private Date expirationDate;
	private EvaluationStatus status;
	private String name;
	private String evalTime;
	private BigDecimal result;
	private String manager;
	private String applicant;
	private String approvalResult;
	
	public ViewEvaluation() {
	 // Constructor default
	}

	@Id
	@Column(name = "id_evaluation", insertable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getIdEvaluation() {
		return idEvaluation;
	}

	public void setIdEvaluation(Long idEvaluation) {
		this.idEvaluation = idEvaluation;
	}

	@Column(insertable = false, updatable = false)
	public Integer getQuestions() {
		return questions;
	}

	public void setQuestions(Integer questions) {
		this.questions = questions;
	}

	@Column(name = "pass_percent", insertable = false, updatable = false)
	public BigDecimal getPassPercent() {
		return passPercent;
	}

	public void setPassPercent(BigDecimal passPercent) {
		this.passPercent = passPercent;
	}

	@Column(name = "creation_date", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "expiration_date", insertable = false, updatable = false)
	@Temporal(TemporalType.DATE)
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Column(name = "status", insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	public EvaluationStatus getStatus() {
		return status;
	}

	public void setStatus(EvaluationStatus status) {
		this.status = status;
	}

	@Column(insertable = false, updatable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "eval_time", insertable = false, updatable = false)
	public String getEvalTime() {
		return evalTime;
	}

	public void setEvalTime(String evalTime) {
		this.evalTime = evalTime;
	}

	@Column(insertable = false, updatable = false)
	public BigDecimal getResult() {
		return result;
	}

	public void setResult(BigDecimal result) {
		this.result = result;
	}

	@Column(name = "owner", insertable = false, updatable = false)
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getApplicant() {
		return applicant;
	}

	@Column(insertable = false, updatable = false)
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	@Column(name = "approval_result", insertable = false, updatable = false)
	public String getApprovalResult() {
		return approvalResult;
	}

	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}
	
}
