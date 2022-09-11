package bartlomiejzienkiewicz.forumapi.Controller.Post;

import bartlomiejzienkiewicz.forumapi.Dao.Post.PostDao;
import bartlomiejzienkiewicz.forumapi.Entity.Post.Post;
import bartlomiejzienkiewicz.forumapi.Entity.User.User;
import bartlomiejzienkiewicz.forumapi.Repository.PostRepository.PostServiceImpl;
import bartlomiejzienkiewicz.forumapi.Repository.UserRepository.UserServiceImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class GetPostsOfSpecificUser {


    UserServiceImpl userService;
    PostServiceImpl postService;

    @Autowired
    public GetPostsOfSpecificUser(UserServiceImpl userService, PostServiceImpl postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping(path = "/get_posts_by_username/{username}")
    public ResponseEntity<String> getPostsByUsername(@PathVariable("username") String username){
        //get user that we want posts from
        User user = userService.findByUsername(username);

        List<PostDao> postDaoList = new ArrayList<>();

        Gson gson = new Gson();

        //if user is found
        if(user != null){
            //convert posts to postDaos
            for(Post post : postService.findAll()){
                PostDao postDao = new PostDao();
                postDao.convertToDao(post);
                postDaoList.add(postDao);
            }
            //sort daos by date of creation
            Collections.sort(postDaoList);

            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(postDaoList));
        }
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

    }

}
