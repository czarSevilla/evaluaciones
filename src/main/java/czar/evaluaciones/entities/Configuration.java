package czar.evaluaciones.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ev_configurations")
public class Configuration implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idConfiguration;
    private String key;
    private String value;
    private String description;
    
    public Configuration() {
     // Constructor default
    }

    @Id
    @Column(name = "id_configuration")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getIdConfiguration() {
        return idConfiguration;
    }

    public void setIdConfiguration(Long idConfiguration) {
        this.idConfiguration = idConfiguration;
    }

    @Column(name = "config_key", nullable = false)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "config_value", nullable = false)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

   @Column(name = "config_desc", nullable = false)
   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }
}
