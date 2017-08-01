package czar.evaluaciones.dtos;

import java.io.Serializable;

public class CategoryCloudDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String label;
	
    private int strength;
    
    public CategoryCloudDto() {
    	// Constructor default
    }
    
    public CategoryCloudDto(String key, Integer value) {
    	this.label = key;
    	this.strength = value;
    }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}
}
