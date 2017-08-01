package czar.evaluaciones.entities;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import czar.evaluaciones.enums.QuestionStatus;

@Entity
@Table(name = "ev_questions")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idQuestion;
    private Long idCreator;
    private String text;
    private String body;
    private Date creationDate;
    private Set<Category> categories;
    private Set<Answer> answers;
    private QuestionStatus status;
    
    public Question() {
        categories = new HashSet<>();
        answers = new HashSet<>();
    }

    @Id
    @Column(name = "id_question")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Long idQuestion) {
        this.idQuestion = idQuestion;
    }

    @Column(name = "id_creator", nullable = false)
    public Long getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(Long idCreator) {
        this.idCreator = idCreator;
    }

    @Column(nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(nullable = false)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    @Column(name = "question_status", nullable = false)
    @Enumerated(EnumType.STRING)
    public QuestionStatus getStatus() {
        return status;
    }

    public void setStatus(QuestionStatus status) {
        this.status = status;
    }
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ev_questions_categories", joinColumns = @JoinColumn(name = "id_question"), inverseJoinColumns = @JoinColumn(name = "id_category"))
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

    @OneToMany(fetch=FetchType.EAGER, mappedBy = "question", cascade = CascadeType.ALL)
    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }
    
    @Transient
    public void addAnswer(Answer answer) {
    	answer.setQuestion(this);
        this.answers.add(answer);
    }    
}
