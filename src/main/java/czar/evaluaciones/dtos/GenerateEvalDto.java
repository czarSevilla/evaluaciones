package czar.evaluaciones.dtos;

import java.io.Serializable;
import java.util.List;

import czar.evaluaciones.enums.SourceEvaluation;

public class GenerateEvalDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private SourceEvaluation source;
    private int step;
    private List<Long> idsCategories;
    private Integer questions;
    private String email;
    private Integer passPercent;
    private String nameApplicant;
    private String name;
    private Long idExam;
    private int evalMinutes;

    public GenerateEvalDto() {
     // Constructor default
    }

    public SourceEvaluation getSource() {
        return source;
    }

    public void setSource(SourceEvaluation source) {
        this.source = source;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public List<Long> getIdsCategories() {
        return idsCategories;
    }

    public void setIdsCategories(List<Long> idsCategories) {
        this.idsCategories = idsCategories;
    }

    public Integer getQuestions() {
        return questions;
    }

    public void setQuestions(Integer questions) {
        this.questions = questions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPassPercent() {
        return passPercent;
    }

    public void setPassPercent(Integer passPercent) {
        this.passPercent = passPercent;
    }

    public String getNameApplicant() {
        return nameApplicant;
    }

    public void setNameApplicant(String nameApplicant) {
        this.nameApplicant = nameApplicant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdExam() {
        return idExam;
    }

    public void setIdExam(Long idExam) {
        this.idExam = idExam;
    }

    public int getEvalMinutes() {
        return evalMinutes;
    }

    public void setEvalMinutes(int evalMinutes) {
        this.evalMinutes = evalMinutes;
    }

}
