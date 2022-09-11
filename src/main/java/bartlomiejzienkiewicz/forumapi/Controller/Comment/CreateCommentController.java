package bartlomiejzienkiewicz.forumapi.Controller.Comment;

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
import bartlomiejzienkiewicz.forumapi.Request.CreateCommentRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CreateCommentController {

    private UserServiceImpl userService;
    private PostServiceImpl postService;
    private CommentServiceImpl commentService;
    private NotificationServiceImpl notificationService;

    @Autowired
    public CreateCommentController(UserServiceImpl userService, PostServiceImpl postService, CommentServiceImpl commentService, NotificationServiceImpl notificationService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
        this.notificationService = notificationService;
    }

    @RequestMapping(value = "/create_comment", consumes = "application/json", method = RequestMethod.PUT)
    public ResponseEntity<String> createComment(@RequestBody CreateCommentRequest commentRequest) throws Exception {

        //getting data form request
        String token = commentRequest.getToken();
        String username = commentRequest.getUsername();
        String contents = commentRequest.getContents();
        String idOfPostString = commentRequest.getIdOfPost();
        Long idOfPost = Long.parseLong(idOfPostString);



        //check if jwt token is valid
        JWTAuthentication.isTokenValid(token);

        Post post = new Post();

        //check if post that we are giving comment to, exists
        Optional<Post> optionalPost = postService.findById(idOfPost);
        if (optionalPost.isPresent()) {
            post = optionalPost.get();


            //getting author entity
            User user = userService.findByUsername(username);

            //creating comment and adding it to database
            Comment comment = Comment.builder().contents(contents).numberOfLikes(0)
                    .utilDate(new Date(System.currentTimeMillis())).user(user).post(post).username(username)
                    .idOfCommentItAnswers(-1L).isItAnswer(false).numberOfAnswers(0).numberOfLikes(0).build();
            post.addComment(comment);

            commentService.save(comment);


            if(!commentRequest.getUsername().equals(post.getUsername())){
                User notifiedUser = post.getUser(); // getting author of post

                //building notification
                Notification notification = Notification.builder()
                        .contents("commented your post")
                        .fromWho(commentRequest.getUsername())
                        .user(notifiedUser)
                        .timeOfCreation(new Date(System.currentTimeMillis()))
                        .idOfPost(post.getId())
                        .notificationType(NotificationType.COMMENT)
                        .build();

                //adding notification to author of post

                notification.setIdOfPost(post.getId());
                notifiedUser.addNotification(notification);
                notificationService.save(notification);
                userService.save(notifiedUser);
            }




            Gson gson = new Gson();
            String json = gson.toJson("ok");
            return ResponseEntity.status(HttpStatus.OK).body(json);
            }

        Gson gson = new Gson();
        String json = gson.toJson("error");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);

    }
}