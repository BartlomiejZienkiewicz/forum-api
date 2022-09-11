package bartlomiejzienkiewicz.forumapi.Controller.Notification;

import bartlomiejzienkiewicz.forumapi.Dao.Notification.NotificationDao;
import bartlomiejzienkiewicz.forumapi.Entity.Notification.Notification;
import bartlomiejzienkiewicz.forumapi.Entity.User.User;
import bartlomiejzienkiewicz.forumapi.JWT.JWTAuthentication;
import bartlomiejzienkiewicz.forumapi.Repository.NotificationRepository.NotificationServiceImpl;
import bartlomiejzienkiewicz.forumapi.Repository.UserRepository.UserServiceImpl;
import bartlomiejzienkiewicz.forumapi.Request.GetNotificationsRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GetNotificationsController {

    NotificationServiceImpl notificationService;
    UserServiceImpl userService;

    @Autowired
    public GetNotificationsController(NotificationServiceImpl notificationService, UserServiceImpl userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @RequestMapping(value = "/get_notifications", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<String> getNotifications(@RequestBody GetNotificationsRequest getNotificationsRequest){

        //get data from request
        String token = getNotificationsRequest.getToken();
        String username = getNotificationsRequest.getUsername();

        //check if token is valid
        JWTAuthentication.isTokenValid(token);
        //get notificated user
        User user = userService.findByUsername(username);


        Gson gson = new Gson();

        //if user is found
        if( user != null){
            //find notifications of user
            List<Notification> notificationList = notificationService.getNotificationsByUser(user);
            List<NotificationDao> notificationDaos  = new ArrayList<>();

            //convert notificationList to notificationDaos
            for(Notification notification : notificationList){
                NotificationDao notificationDao = new NotificationDao();
                notificationDao.convertToDao(notification);
                notificationDaos.add(notificationDao);
            }

            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(notificationDaos));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(gson.toJson("error"));
        }

    }
}
