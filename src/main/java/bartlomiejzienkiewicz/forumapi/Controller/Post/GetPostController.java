package bartlomiejzienkiewicz.forumapi.Controller.Post;

import bartlomiejzienkiewicz.forumapi.Dao.Post.PostDao;
import bartlomiejzienkiewicz.forumapi.Entity.Post.Post;
import bartlomiejzienkiewicz.forumapi.Repository.PostRepository.PostServiceImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@CrossOrigin
public class GetPostController {

    private PostServiceImpl postService;


    @Autowired
    public GetPostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @GetMapping(path = "/get_posts")
    public ResponseEntity<String> getPosts() {


        Post post = new Post();

        List<PostDao> postDaoList = new ArrayList<>();

        //convert to daos
        for(Post post1 : postService.findAll()){
            PostDao postDao = new PostDao();
            postDao.convertToDao(post1);
            postDaoList.add(postDao);

        }

        //sort by date of creation
        Collections.sort(postDaoList);


        Gson gson = new Gson();
        String json = gson.toJson(postDaoList);
        return new ResponseEntity<String>(json,HttpStatus.OK);


    }
    @GetMapping(path = "/get_posts/{postId}")
    public ResponseEntity<String> getPostById(@PathVariable("postId") Long id){
        //get post that we want to get
        Optional<Post> optionalPost = postService.findById(id);
        //if post is present
        if(optionalPost.isPresent()){
            //converting post to dao
            Post post = optionalPost.get();
            PostDao postDao = new PostDao();
            postDao.convertToDao(post);

            Gson gson = new Gson();
            String json = gson.toJson(postDao);
            return new ResponseEntity<String>(json,HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }



}
