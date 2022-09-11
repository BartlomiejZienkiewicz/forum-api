package bartlomiejzienkiewicz.forumapi.Controller.Answer;

import bartlomiejzienkiewicz.forumapi.Dao.Comment.CommentDao;
import bartlomiejzienkiewicz.forumapi.Entity.Comment.Comment;
import bartlomiejzienkiewicz.forumapi.Repository.CommentRepository.CommentServiceImpl;
import com.google.gson.Gson;
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
public class GetAnswersController {

    CommentServiceImpl commentService;

    public GetAnswersController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @GetMapping(path = "/get_answers/{id}")
    public ResponseEntity<String> getAnswers(@PathVariable("id") Long id){
        Gson gson = new Gson();

        //find answers by id of comment
        List<Comment> commentList = commentService.findCommentsByIdOfCommentThatTheyAreAnsweringTo(id);

        List<CommentDao> commentDaoList = new ArrayList<>();

        //converting commentList to commentDaoList
        for(Comment comment1 :commentList ){
            if(comment1.getIsItAnswer()){
                CommentDao commentDao = new CommentDao();
                commentDao.convertToDao(comment1);
                commentDaoList.add(commentDao);
            }


        }
        //sorting list by the date of creation of the entities
        Collections.sort(commentDaoList);

        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(commentDaoList));

    }

}
