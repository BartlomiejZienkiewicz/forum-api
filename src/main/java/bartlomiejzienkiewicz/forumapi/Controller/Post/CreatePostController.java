package bartlomiejzienkiewicz.forumapi.Controller.Post;

import bartlomiejzienkiewicz.forumapi.Entity.Post.Post;
import bartlomiejzienkiewicz.forumapi.Entity.User.User;
import bartlomiejzienkiewicz.forumapi.JWT.JWTAuthentication;
import bartlomiejzienkiewicz.forumapi.Repository.PostRepository.PostServiceImpl;
import bartlomiejzienkiewicz.forumapi.Repository.UserRepository.UserServiceImpl;
import bartlomiejzienkiewicz.forumapi.Request.CreatePostRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CreatePostController {

    PostServiceImpl postService;
    UserServiceImpl userService;

    public CreatePostController(PostServiceImpl postService, UserServiceImpl userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @RequestMapping(value = "/createPost", consumes = "application/json", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<String> createPost(@RequestBody CreatePostRequest createPostRequest){

            //check if token is valid
            JWTAuthentication.isTokenValid(createPostRequest.getToken());

            try {
                //get author of post
                User user = userService.findByUsername(createPostRequest.getUsername());

                    //build created post
                    Post post = Post.builder().contents(createPostRequest.getContents())
                            .utilDate(new Date(System.currentTimeMillis()))
                            .user(user).username(user.getUsername()).build();
                    //save post
                    postService.save(post);

                    return new ResponseEntity<String>(HttpStatus.OK);

            }
            catch (Exception e){
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

            }


    }
}
