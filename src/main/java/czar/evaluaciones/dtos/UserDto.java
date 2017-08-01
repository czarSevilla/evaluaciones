package czar.evaluaciones.dtos;

import java.util.List;

import czar.evaluaciones.entities.User;

public class UserDto extends User {

	private static final long serialVersionUID = 1L;
	private String verifyPassword;
	private List<User> users;
	private int page;
    private int size;
    private int totalPages;
    private long totalItems;
    private String direction;
    private String authority;
    private String currentPassword;
	
	public UserDto() {
	 // Constructor default
	}
	
	public UserDto(Long pIdUser, String pName) {
		this.setIdUser(pIdUser);
		this.setName(pName);
	}

	public String getVerifyPassword() {
		return verifyPassword;
	}

	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
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

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

}
