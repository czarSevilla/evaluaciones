package czar.evaluaciones.dtos;

import java.io.Serializable;
import java.util.List;

import czar.evaluaciones.entities.ViewEvaluation;
import czar.evaluaciones.enums.EvaluationStatus;

public class ViewEvalDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<ViewEvaluation> evaluations;
    private int page;
    private int size;
    private String direction;
    private String propertySort;
    private String name;
    private int totalPages;
    private long totalItems;
    private EvaluationStatus searchStatus;
    private String searchResultado;
    private String searchManager;

    public ViewEvalDto() {
        // Constructor default
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getPropertySort() {
        return propertySort;
    }

    public void setPropertySort(String propertySort) {
        this.propertySort = propertySort;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<ViewEvaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<ViewEvaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public EvaluationStatus getSearchStatus() {
        return searchStatus;
    }

    public void setSearchStatus(EvaluationStatus searchStatus) {
        this.searchStatus = searchStatus;
    }

    public String getSearchResultado() {
        return searchResultado;
    }

    public void setSearchResultado(String searchResultado) {
        this.searchResultado = searchResultado;
    }

    public String getSearchManager() {
        return searchManager;
    }

    public void setSearchManager(String searchManager) {
        this.searchManager = searchManager;
    }

}
