package bartlomiejzienkiewicz.forumapi.Controller.Message;

import bartlomiejzienkiewicz.forumapi.Entity.Conversation.Conversation;
import bartlomiejzienkiewicz.forumapi.Entity.Message.Message;
import bartlomiejzienkiewicz.forumapi.Entity.Notification.Notification;
import bartlomiejzienkiewicz.forumapi.Entity.Notification.NotificationType;
import bartlomiejzienkiewicz.forumapi.Entity.User.User;
import bartlomiejzienkiewicz.forumapi.JWT.JWTAuthentication;
import bartlomiejzienkiewicz.forumapi.Repository.ConversationRepository.ConversationService;
import bartlomiejzienkiewicz.forumapi.Repository.MessageRepository.MessageService;
import bartlomiejzienkiewicz.forumapi.Repository.NotificationRepository.NotificationServiceImpl;
import bartlomiejzienkiewicz.forumapi.Repository.UserRepository.UserServiceImpl;
import bartlomiejzienkiewicz.forumapi.Request.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class SendMessageController {

    private MessageService messageService;

    private ConversationService conversationService;

    private UserServiceImpl userService;

    private NotificationServiceImpl notificationService;

    @Autowired
    public SendMessageController(MessageService messageService, ConversationService conversationService, UserServiceImpl userService, NotificationServiceImpl notificationService) {
        this.messageService = messageService;
        this.conversationService = conversationService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @RequestMapping(value = "/send_message", consumes = "application/json", method = RequestMethod.PUT)
    public ResponseEntity<String> sendMessage(@RequestBody SendMessageRequest sendMessageRequest){


        //get data from request
        String token = sendMessageRequest.getToken();
        String authorsUsername = sendMessageRequest.getAuthorsUsername();
        String secondUsersUsername = sendMessageRequest.getSecondUsersUsername();
        String contents = sendMessageRequest.getContents();

        //check if token is valid
        JWTAuthentication.isTokenValid(token);

        //get author and second user
        User author = userService.findByUsername(authorsUsername);

        User secondUser = userService.findByUsername(secondUsersUsername);

        //get conversation of two users
        Conversation conversation = conversationService.findConversationByIdsOfTwoUsers(author.getId() ,secondUser.getId());

        //if conversation doesnt yet exists it gets created and saved
        if(conversation == null){
            Conversation newConversation = Conversation.builder().idOfFirstUser(author.getId()).idOfSecondUser(secondUser.getId()).build();
            conversation = newConversation;
            conversationService.save(conversation);
        }

        //user that gets notification about message
        User notifiedUser = userService.findByUsername(secondUsersUsername);

        //create notification
        Notification notification = Notification.builder()
                .contents("messaged you")
                .fromWho(authorsUsername)
                .user(notifiedUser)
                .timeOfCreation(new Date(System.currentTimeMillis()))
                .notificationType(NotificationType.MESSAGE)
                .build();


        //add notification to user and save
        notifiedUser.addNotification(notification);
        notificationService.save(notification);
        userService.save(notifiedUser);


        //create message
        Message message = Message.builder().user(author).utilDate(new Date(System.currentTimeMillis()))
                .contents(contents).conversation(conversation).build();

        //add message to author
        author.addMessage(message);

        //update author
        userService.save(author);

        //save message
        messageService.save(message);

        //add message to conversation and update
        conversation.addMessage(message);
        conversationService.save(conversation);

        return new ResponseEntity(HttpStatus.OK);


    }



}
