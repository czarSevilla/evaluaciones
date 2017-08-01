package czar.evaluaciones.dtos;

import java.io.Serializable;
import java.util.List;

import czar.evaluaciones.entities.Category;

public class CategoryDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Category> categories;
    private String description;
    private int page;
    private int size;
    private String direction;
    private int totalPages;
    private long totalItems;
    
	public CategoryDto() {
	 // Constructor default
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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
}
