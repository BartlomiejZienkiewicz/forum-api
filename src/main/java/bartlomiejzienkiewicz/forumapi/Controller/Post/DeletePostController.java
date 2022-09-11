package bartlomiejzienkiewicz.forumapi.Controller.Post;

import bartlomiejzienkiewicz.forumapi.JWT.JWTAuthentication;
import bartlomiejzienkiewicz.forumapi.Repository.PostRepository.PostServiceImpl;
import bartlomiejzienkiewicz.forumapi.Request.DeletePostRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class DeletePostController {

    PostServiceImpl postService;

    @Autowired
    public DeletePostController(PostServiceImpl postService) {
        this.postService = postService;
    }
    @RequestMapping(value = "/delete_post", consumes = "application/json", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePost(@RequestBody DeletePostRequest deletePostRequest){
        //get data request
        Long id = deletePostRequest.getId();
        String jwt = deletePostRequest.getJwt();
        //validate token
        JWTAuthentication.isTokenValid(jwt);

        Gson gson = new Gson();

        //delete post
        postService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson("ok"));
        }


    }

