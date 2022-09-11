package bartlomiejzienkiewicz.forumapi.Repository.NotificationRepository;

import bartlomiejzienkiewicz.forumapi.Entity.Notification.Notification;
import bartlomiejzienkiewicz.forumapi.Entity.User.User;
import bartlomiejzienkiewicz.forumapi.Repository.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements EntityService<Notification> {


    NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void save(Notification entity) {
        notificationRepository.save(entity);
    }

    @Override
    public void deleteAll() {
        notificationRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return notificationRepository.findById(id);
    }

    public List<Notification> findAll(){
        return notificationRepository.findAll();
    }


    public List<Notification> getNotificationsByUser(User user){
        List<Notification> notificationList = findAll().stream().filter(notification -> notification.getUser().equals(user)).collect(Collectors.toList());
        return notificationList;
    }

}
