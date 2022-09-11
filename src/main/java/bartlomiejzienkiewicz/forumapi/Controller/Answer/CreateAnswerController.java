package bartlomiejzienkiewicz.forumapi.Controller.Answer;

import bartlomiejzienkiewicz.forumapi.Entity.Comment.Comment;
import bartlomiejzienkiewicz.forumapi.Entity.Notification.Notification;
import bartlomiejzienkiewicz.forumapi.Entity.Notification.NotificationType;
import bartlomiejzienkiewicz.forumapi.Entity.Post.Post;
import bartlomiejzienkiewicz.forumapi.Entity.User.User;
import bartlomiejzienkiewicz.forumapi.JWT.JWTAuthentication;
import bartlomiejzienkiewicz.forumapi.Repository.CommentRepository.CommentServiceImpl;
import bartlomiejzienkiewicz.forumapi.Repository.NotificationRepository.NotificationServiceImpl;
import bartlomiejzienkiewicz.forumapi.Repository.PostRepository.PostServiceImpl;
import bartlomiejzienkiewicz.forumapi.Repository.UserRepository.UserServiceImpl;
import bartlomiejzienkiewicz.forumapi.Request.CreateAnswerRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class CreateAnswerController {

    CommentServiceImpl commentService;
    UserServiceImpl userService;
    PostServiceImpl postService;
    NotificationServiceImpl notificationService;

    @Autowired
    public CreateAnswerController(CommentServiceImpl commentService, UserServiceImpl userService, PostServiceImpl postService, NotificationServiceImpl notificationService) {
        this.commentService = commentService;
        this.userService = userService;
        this.postService = postService;
        this.notificationService = notificationService;
    }

    @RequestMapping(value = "/create_answer", consumes = "application/json", method = RequestMethod.PUT)
    public ResponseEntity<String> createAnswer(@RequestBody CreateAnswerRequest createAnswerRequest){

        //checking if token is valid
        JWTAuthentication.isTokenValid(createAnswerRequest.getToken());

        //getting data from request body
        Long idOfAnsweredComment = createAnswerRequest.getIdOfAnsweredCommend();


        Optional<Comment> optionalComment = commentService.findById(idOfAnsweredComment); //comment which gets answer

        User user = userService.findByUsername(createAnswerRequest.getUsername()); //author of answer

        Optional<Post> optionalPost = postService.findById(createAnswerRequest.getIdOfPost()); //post that user puts answer in

        Gson gson = new Gson();

        //if author and comment which gets answer are found
        if(optionalComment.isPresent() && user != null && optionalComment.isPresent()) {



            Comment comment = optionalComment.get(); //comment which gets answer
            comment.setNumberOfAnswers(comment.getNumberOfAnswers() + 1); //adding 1 to number of answers

            //building answer
            Comment comment2 = Comment.builder().contents(createAnswerRequest.getContents())
                    .username(createAnswerRequest.getUsername())
                    .user(user)
                    .post(optionalPost.get())
                    .utilDate(new Date(System.currentTimeMillis()))
                    .numberOfLikes(0)
                    .idOfCommentItAnswers(idOfAnsweredComment)
                    .isItAnswer(true)
                    .numberOfAnswers(0).build();

            commentService.save(comment2);

            //getting post that user puts answer in and updating it
            Post post = optionalPost.get();
            post.setNumberOfComments(post.getNumberOfComments() + 1); //adding 1 to number of comments
            postService.save(post);


            //saving notification

            if(!post.getUsername().equals(createAnswerRequest.getUsername())){
                User notifiedUser = post.getUser(); // getting author of post

                //building notification
                Notification notification = Notification.builder()
                        .contents("commented your post")
                        .timeOfCreation(new Date(System.currentTimeMillis()))
                        .user(notifiedUser)
                        .fromWho(createAnswerRequest.getUsername())
                        .idOfPost(post.getId())
                        .notificationType(NotificationType.COMMENT)
                        .build();

                //saving

                notification.setIdOfPost(post.getId());
                notificationService.save(notification);

                notifiedUser.addNotification(notification);
                userService.save(notifiedUser);
            }


            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson("ok"));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(gson.toJson("error"));





    }
}
