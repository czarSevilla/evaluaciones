package czar.evaluaciones.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ev_categories")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long idCategory;
    
    private String description;
    
    public Category() {
     // Constructor default
    }
    
    public Category(Long pIdCategory) {
    	this.idCategory = pIdCategory;
    }

    @Id
    @Column(name = "id_category")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
