package bartlomiejzienkiewicz.forumapi.Controller.Conversation;

import bartlomiejzienkiewicz.forumapi.Dao.Conversation.ConversationDao;
import bartlomiejzienkiewicz.forumapi.Entity.Conversation.Conversation;
import bartlomiejzienkiewicz.forumapi.Entity.User.User;
import bartlomiejzienkiewicz.forumapi.JWT.JWTAuthentication;
import bartlomiejzienkiewicz.forumapi.Repository.ConversationRepository.ConversationService;
import bartlomiejzienkiewicz.forumapi.Repository.UserRepository.UserServiceImpl;
import bartlomiejzienkiewicz.forumapi.Request.DoesConversationExist;
import bartlomiejzienkiewicz.forumapi.Request.GetUsersConversationsRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class ConversationController {


    ConversationService conversationService;

    UserServiceImpl userService;

    @Autowired
    public ConversationController(ConversationService conversationService, UserServiceImpl userService) {
        this.conversationService = conversationService;
        this.userService = userService;
    }


    @RequestMapping(value = "/does_conversation_exist", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<String> doesConversationExist(@RequestBody DoesConversationExist doesConversationExist){
        //get data from request
        String token = doesConversationExist.getToken();
        String firstUsersUsername = doesConversationExist.getFirstUsersUsername();
        String secondUsersUsername = doesConversationExist.getSecondUsersUsername();

        //check if token is correct
        JWTAuthentication.isTokenValid(token);

        //find users of conversation
        User firstUser = userService.findByUsername(firstUsersUsername);

        User secondUser = userService.findByUsername(secondUsersUsername);

        //get conversation
        Conversation conversation = conversationService.findConversationByIdsOfTwoUsers(firstUser.getId() ,secondUser.getId());

        if(conversation == null){
            return ResponseEntity.status(HttpStatus.OK).body("false");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body("true");

        }

    }
    @PostMapping(path = "/get_users_conversations")
    public ResponseEntity<String> getUsersConversations(@RequestBody GetUsersConversationsRequest getUsersConversationsRequest){


        //get data from request
        String token = getUsersConversationsRequest.getToken();
        String username = getUsersConversationsRequest.getUsersUsername();

        //check if token is correct
        JWTAuthentication.isTokenValid(token);

        try{
            //user that we want conversations from
            User user = userService.findByUsername(username);
            //conversations of user
            List<Conversation> conversations = conversationService.findByIdOfUser(user.getId());
            List<ConversationDao> conversationDaos = new ArrayList<>();
            //converting conversations to conversationDao
            for(Conversation conversation: conversations){
                ConversationDao conversationDao = new ConversationDao();

                //checking if conversation belongs to user by two if statements
                if(conversation.getIdOfFirstUser().equals(user.getId())){
                    Optional<User> optionalUser = userService.findById(conversation.getIdOfSecondUser());
                    if(optionalUser.isPresent()){
                        conversationDao.setSecondUsersUsername(optionalUser.get().getUsername());
                        conversationDaos.add(conversationDao);
                    }
                }
                else if(conversation.getIdOfSecondUser().equals(user.getId())){
                    Optional<User> optionalUser = userService.findById(conversation.getIdOfFirstUser());
                    if(optionalUser.isPresent()){
                        conversationDao.setSecondUsersUsername(optionalUser.get().getUsername());
                        conversationDaos.add(conversationDao);

                    }
                }

            }
            Gson gson = new Gson();
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(conversationDaos));

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
}
