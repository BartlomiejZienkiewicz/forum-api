package bartlomiejzienkiewicz.forumapi.Controller.Notification;

import bartlomiejzienkiewicz.forumapi.JWT.JWTAuthentication;
import bartlomiejzienkiewicz.forumapi.Repository.NotificationRepository.NotificationServiceImpl;
import bartlomiejzienkiewicz.forumapi.Request.DeleteNotificationRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class DeleteNotificationController {

    NotificationServiceImpl notificationService;

    @Autowired
    public DeleteNotificationController(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    @RequestMapping(value = "/delete_notification", consumes = "application/json", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteNotification(@RequestBody DeleteNotificationRequest deleteNotificationRequest){

        //get data from authentication
        String token = deleteNotificationRequest.getToken();
        Long idOfNotification = deleteNotificationRequest.getNotificationId();

        //check if token is valid
        JWTAuthentication.isTokenValid(token);

        Gson gson = new Gson();

        //delete notification
        notificationService.deleteById(idOfNotification);
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson("ok"));



    }
}
