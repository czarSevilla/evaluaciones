package czar.evaluaciones.dtos;

import static czar.evaluaciones.enums.QuestionStatus.ACTIVA;
import static czar.evaluaciones.enums.QuestionStatus.OBSOLETA;
import static czar.evaluaciones.enums.QuestionStatus.REVISION;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import czar.evaluaciones.entities.Question;
import czar.evaluaciones.enums.QuestionStatus;

public class QuestionDto extends Question implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private List<Question> questions;
    
    private int page;
    private int size;
    private int totalPages;
    private long totalItems;
    private String direction;
    private List<QuestionStatus> comboStatus;
    private List<String> answersTexts;
    private List<Integer> answersCorrects;
    private List<String> categoriesDescriptions;
    private QuestionStatus searchStatus;
    private String searchCategory;
    
    public QuestionDto() {
        comboStatus = Arrays.asList(REVISION, ACTIVA, OBSOLETA);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<QuestionStatus> getComboStatus() {
        return comboStatus;
    }

    public void setComboStatus(List<QuestionStatus> comboStatus) {
        this.comboStatus = comboStatus;
    }

    public List<String> getAnswersTexts() {
        return answersTexts;
    }

    public void setAnswersTexts(List<String> answersTexts) {
        this.answersTexts = answersTexts;
    }

    public List<Integer> getAnswersCorrects() {
        return answersCorrects;
    }

    public void setAnswersCorrects(List<Integer> answersCorrects) {
        this.answersCorrects = answersCorrects;
    }

    public List<String> getCategoriesDescriptions() {
        return categoriesDescriptions;
    }

    public void setCategoriesDescriptions(List<String> categoriesDescriptions) {
        this.categoriesDescriptions = categoriesDescriptions;
    }

	public QuestionStatus getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(QuestionStatus searchStatus) {
		this.searchStatus = searchStatus;
	}

	public String getSearchCategory() {
		return searchCategory;
	}

	public void setSearchCategory(String searchCategory) {
		this.searchCategory = searchCategory;
	}
}
