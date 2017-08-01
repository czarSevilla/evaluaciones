package czar.evaluaciones.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import czar.evaluaciones.entities.Notification;
import czar.evaluaciones.enums.NotificationStatus;

@Repository("notificationRepository")
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByStatus(NotificationStatus notificationStatus);
}
