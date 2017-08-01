package czar.evaluaciones.dtos;

import java.io.Serializable;

public class ComboDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long key;
    
    private String value;
    
    public ComboDto() {
     // Constructor default
    }
    
    public ComboDto(Long pKey, String pValue) {
        this.key = pKey;
        this.value = pValue;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof ComboDto)) {
			return false;
		}
		return String.format("%d", key)
				.equals(String.format("%d", ((ComboDto) o).key));
	}
	
	@Override
	public int hashCode() {
		return String.format("%d", key).hashCode();
	}
}
