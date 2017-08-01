package czar.evaluaciones.dtos;

import java.io.Serializable;

public class PaginadorDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int page;
    private int size;
    private int totalPages;
    private long totalItems;
    private String direction;
	
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
    
    
}
