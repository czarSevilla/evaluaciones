package czar.evaluaciones.dtos;

import java.io.Serializable;
import java.util.List;

import czar.evaluaciones.entities.Exam;

public class ExamDto extends Exam implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Exam> exams;
    private int page;
    private int size;
    private int totalPages;
    private long totalItems;
    private String direction;
    private List<Long> idsCategories;
    private List<ComboDto> comboCategories;
    
    public ExamDto() {
        // Constructor default
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
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

	public List<Long> getIdsCategories() {
		return idsCategories;
	}

	public void setIdsCategories(List<Long> idsCategories) {
		this.idsCategories = idsCategories;
	}

	public List<ComboDto> getComboCategories() {
		return comboCategories;
	}

	public void setComboCategories(List<ComboDto> comboCategories) {
		this.comboCategories = comboCategories;
	}
}
