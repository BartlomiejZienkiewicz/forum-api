package bartlomiejzienkiewicz.forumapi.Repository.NotificationRepository;

import bartlomiejzienkiewicz.forumapi.Entity.Notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification , Long> {
}
