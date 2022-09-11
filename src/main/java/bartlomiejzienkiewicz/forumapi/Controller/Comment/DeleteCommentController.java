package bartlomiejzienkiewicz.forumapi.Controller.Comment;

import bartlomiejzienkiewicz.forumapi.Entity.Comment.Comment;
import bartlomiejzienkiewicz.forumapi.Entity.Post.Post;
import bartlomiejzienkiewicz.forumapi.JWT.JWTAuthentication;
import bartlomiejzienkiewicz.forumapi.Repository.CommentRepository.CommentServiceImpl;
import bartlomiejzienkiewicz.forumapi.Repository.PostRepository.PostServiceImpl;
import bartlomiejzienkiewicz.forumapi.Request.DeleteCommentRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DeleteCommentController {

    private CommentServiceImpl commentService;

    private PostServiceImpl postService;

    @Autowired
    public DeleteCommentController(CommentServiceImpl commentService, PostServiceImpl postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @RequestMapping(value = "/delete_comment", consumes = "application/json", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteComment(@RequestBody DeleteCommentRequest deleteCommentRequest){

        //getting data from request
        Long id = deleteCommentRequest.getId();
        String jwt  = deleteCommentRequest.getJwt();

        JWTAuthentication.isTokenValid(jwt);

        Gson gson = new Gson();

        //find comment that is going to be deleted
        Optional<Comment> optionalComment = commentService.findById(id);
        //check if comment that is going to be deleted exist
        if(optionalComment.isPresent()){
            //getting comment
            Comment comment = optionalComment.get();

            // if comment is an answer to another comment than subtract 1 from number of answers from answered comment
            if(comment.getIsItAnswer()){
                Optional<Comment> optionalComment1 = commentService.findById(comment.getIdOfCommentItAnswers());
                if(optionalComment1.isPresent()){
                    Comment comment1 = optionalComment1.get();
                    comment1.setNumberOfAnswers(comment1.getNumberOfAnswers() - 1);
                    commentService.save(comment1);
                }
            }
            //subtract 1 from number of comments from post that had deleted comment
            Post post = optionalComment.get().getPost();
            post.setNumberOfComments(post.getNumberOfComments() - 1);
            //save updated post
            postService.save(post);

            //delete comment
            commentService.deleteById(id);



            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson("ok"));

        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(gson.toJson("forbidden"));
        }

    }

}
