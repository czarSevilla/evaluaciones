package czar.evaluaciones.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "ev_authorities")
public class Authority implements Serializable, GrantedAuthority {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long idAuthority;
    private String authority;
    private User user;
    
    public Authority() {
     // Constructor default
    }
    
    public Authority(String pAuthority) {
        this.authority = pAuthority;
    }

    @Id
    @Column(name = "id_authority")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getIdAuthority() {
        return idAuthority;
    }

    public void setIdAuthority(Long idAuthority) {
        this.idAuthority = idAuthority;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", nullable=false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    @Column
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
    @Override
    public String toString() {
    	return authority;
    }
}
