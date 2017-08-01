package czar.evaluaciones.entities;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ev_exams")
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idExam;
    
    private String name;
    
    private int questions;
    
    private BigDecimal passPercent;
    
    private Set<Category> categories;
    
    private int examMinutes;
    
    public Exam() {
        categories = new HashSet<>();
    }

    @Id
    @Column(name = "id_exam")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getIdExam() {
        return idExam;
    }

    public void setIdExam(Long idExam) {
        this.idExam = idExam;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }
    
    @Column(name = "pass_percent", nullable = false)
    public BigDecimal getPassPercent() {
        return passPercent;
    }

    public void setPassPercent(BigDecimal passPercent) {
        this.passPercent = passPercent;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ev_exams_categories", joinColumns = @JoinColumn(name = "id_exam"), inverseJoinColumns = @JoinColumn(name = "id_category"))
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

    @Column(name = "exam_minutes", nullable = false)
    public int getExamMinutes() {
        return examMinutes;
    }

    public void setExamMinutes(int examMinutes) {
        this.examMinutes = examMinutes;
    }

    
}
