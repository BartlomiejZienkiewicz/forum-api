package bartlomiejzienkiewicz.forumapi.Controller.Comment;

import bartlomiejzienkiewicz.forumapi.Entity.Comment.Comment;
import bartlomiejzienkiewicz.forumapi.JWT.JWTAuthentication;
import bartlomiejzienkiewicz.forumapi.Repository.CommentRepository.CommentServiceImpl;
import bartlomiejzienkiewicz.forumapi.Request.EditCommentRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class EditCommentController {


    private CommentServiceImpl commentService;

    @Autowired
    public EditCommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/edit_comment", consumes = "application/json", method = RequestMethod.PATCH)
    public ResponseEntity<String> editComment(@RequestBody EditCommentRequest editCommentRequest){

        JWTAuthentication.isTokenValid(editCommentRequest.getJwt());

        //get comment that is going to be edited
        Optional<Comment> optionalComment = commentService.findById(editCommentRequest.getCommentId());

        Gson gson = new Gson();

        //check if comment exists
        if(optionalComment.isPresent()){
            Comment comment = optionalComment.get();
            comment.setContents(editCommentRequest.getEditedText());
            commentService.save(comment);

            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson("ok"));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(gson.toJson("error"));

        }

}
}
