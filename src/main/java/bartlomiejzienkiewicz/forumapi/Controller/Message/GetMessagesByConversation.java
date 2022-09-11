package bartlomiejzienkiewicz.forumapi.Controller.Message;

import bartlomiejzienkiewicz.forumapi.Dao.Message.MessageDao;
import bartlomiejzienkiewicz.forumapi.Entity.Conversation.Conversation;
import bartlomiejzienkiewicz.forumapi.Entity.Message.Message;
import bartlomiejzienkiewicz.forumapi.Entity.User.User;
import bartlomiejzienkiewicz.forumapi.JWT.JWTAuthentication;
import bartlomiejzienkiewicz.forumapi.Repository.ConversationRepository.ConversationService;
import bartlomiejzienkiewicz.forumapi.Repository.MessageRepository.MessageService;
import bartlomiejzienkiewicz.forumapi.Repository.UserRepository.UserServiceImpl;
import bartlomiejzienkiewicz.forumapi.Request.GetMessagesByConversationRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class GetMessagesByConversation {

    private ConversationService conversationService;
    private MessageService messageService;
    private UserServiceImpl userService;

    @Autowired
    public GetMessagesByConversation(ConversationService conversationService, MessageService messageService, UserServiceImpl userService) {
        this.conversationService = conversationService;
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping(path="/get_messages_by_conversation")
    public ResponseEntity<String> getMessagesByConversation(@RequestBody GetMessagesByConversationRequest getConversationRequest){

        //getting data form request
        String firstUsername = getConversationRequest.getFirstUserUsername();
        String secondUsername = getConversationRequest.getSecondUserUsername();
        String token = getConversationRequest.getToken();

        //check if token is valid
        JWTAuthentication.isTokenValid(token);


        //get users that we are getting messages from
        User user1 = userService.findByUsername(firstUsername);
        User user2 = userService.findByUsername(secondUsername);

        //get conversation od two users
        Conversation conversation = conversationService.findConversationByIdsOfTwoUsers(user1.getId(),user2.getId());


        //getting messages that belong to conversation
        List<Message> messageList = messageService.getMessagesByConversation(conversation);

        List<MessageDao> messageDaoList = new ArrayList<>();

        //convert messageList to messageDaoList
        for(Message message: messageList){
            MessageDao messageDao = new MessageDao();
            messageDao.convertToDao(message);
            messageDaoList.add(messageDao);
        }


        //sort messages by time of creation in descending order
        Collections.sort(messageDaoList,new Comparator<MessageDao>() {
            @Override
            public int compare(MessageDao a, MessageDao b) {
                return b.getHowLongAgoInMillis().compareTo(a.getHowLongAgoInMillis());
            }
        });

        Gson gson = new Gson();
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(messageDaoList));
    }

}
