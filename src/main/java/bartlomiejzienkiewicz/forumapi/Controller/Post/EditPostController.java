package bartlomiejzienkiewicz.forumapi.Controller.Post;

import bartlomiejzienkiewicz.forumapi.Entity.Post.Post;
import bartlomiejzienkiewicz.forumapi.JWT.JWTAuthentication;
import bartlomiejzienkiewicz.forumapi.Repository.PostRepository.PostServiceImpl;
import bartlomiejzienkiewicz.forumapi.Request.EditPostRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class EditPostController {

    PostServiceImpl postService;

    @Autowired
    public EditPostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @RequestMapping(value = "/edit_post", consumes = "application/json", method = RequestMethod.PATCH)
    public ResponseEntity<String> editComment(@RequestBody EditPostRequest editPostRequest){

        //check if token is valid
        JWTAuthentication.isTokenValid(editPostRequest.getJwt());

        //get post that will be edited
        Optional<Post> optionalPost = postService.findById(editPostRequest.getPostId());

        Gson gson = new Gson();

        //if post is found
        if(optionalPost.isPresent()){
            //get post
            Post post = optionalPost.get();
            //editing contents
            post.setContents(editPostRequest.getEditedText());
            //save update posts
            postService.save(post);

            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson("ok"));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(gson.toJson("error"));

        }

    }


}
