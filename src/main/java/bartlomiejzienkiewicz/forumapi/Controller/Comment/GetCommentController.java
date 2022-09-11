package bartlomiejzienkiewicz.forumapi.Controller.Comment;

import bartlomiejzienkiewicz.forumapi.Dao.Comment.CommentDao;
import bartlomiejzienkiewicz.forumapi.Entity.Comment.Comment;
import bartlomiejzienkiewicz.forumapi.Repository.CommentRepository.CommentServiceImpl;
import bartlomiejzienkiewicz.forumapi.Repository.PostRepository.PostServiceImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path="/comments/{postId}")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class GetCommentController {

    PostServiceImpl postService;

    CommentServiceImpl commentService;



    @Autowired
    public GetCommentController(PostServiceImpl postService, CommentServiceImpl commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<String> getCommentsByIdOfPost(@PathVariable("postId") Long id){


        List<CommentDao> commentDaoList = new ArrayList<>();

        for(Comment comment1 : commentService.findCommentsByIdOfPost(id)){

            if(comment1.getIsItAnswer()){

            }
            else {

                CommentDao commentDao = new CommentDao();
                commentDao.convertToDao(comment1);
                commentDaoList.add(commentDao);
            }

            }



        //sort list by the date of creation
        Collections.sort(commentDaoList);


        Gson gson = new Gson();
        String json = gson.toJson(commentDaoList);
        return new ResponseEntity<String>(json,HttpStatus.OK);


    }

}
