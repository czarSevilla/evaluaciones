package czar.evaluaciones.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import czar.evaluaciones.enums.NotificationStatus;
import czar.evaluaciones.enums.NotificationType;

@Entity
@Table(name = "ev_notifications")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idNotificacion;
    private String nameApplicant;
    private String emailApplicant;
    private String passwordApplicant;
    private User manager;
    private NotificationStatus status;
    private Date expirationDate;
    private NotificationType type;
    
    public Notification() {
     // Constructor default
    }

    @Id
    @Column(name = "id_notification")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    @Column(name = "name_applicant", nullable = false)
    public String getNameApplicant() {
        return nameApplicant;
    }

    public void setNameApplicant(String nameApplicant) {
        this.nameApplicant = nameApplicant;
    }

    @Column(name = "email_applicant", nullable = false)
    public String getEmailApplicant() {
        return emailApplicant;
    }

    public void setEmailApplicant(String emailApplicant) {
        this.emailApplicant = emailApplicant;
    }

    @Column(name = "plain_password", nullable = false)
    public String getPasswordApplicant() {
        return passwordApplicant;
    }

    public void setPasswordApplicant(String passwordApplicant) {
        this.passwordApplicant = passwordApplicant;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_manager", nullable = false)
    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    @Column(name = "expiration_date", nullable = false)
    @Temporal(TemporalType.DATE)
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Column(name = "type_notification", nullable = false)
    @Enumerated(EnumType.STRING)
	public NotificationType getType() {
		return type;
	}
	
	public void setType(NotificationType type) {
		this.type = type;
	}
    
    
}
