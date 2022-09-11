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

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class GetPostsBySearch {

    private PostServiceImpl postService;

    @Autowired
    public GetPostsBySearch(PostServiceImpl postService) {
        this.postService = postService;
    }

    @GetMapping(path="/get_posts_by_search/{input}")
    public ResponseEntity<String> getPostsBySearch(@PathVariable("input") String input){
        //boolean isFound = user1.getUsername().indexOf(input) !=-1? true: false;
        List<PostDao> postDaoList = new ArrayList<>();

        for(Post post: postService.findAll()){
            boolean isFound = post.getContents().indexOf(input) !=-1? true: false;
            if(isFound){
                PostDao postDao = new PostDao();
                postDao.convertToDao(post);
                postDaoList.add(postDao);
            }
        }
        Gson gson = new Gson();

        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(postDaoList));

    }

}
