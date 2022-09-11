package bartlomiejzienkiewicz.forumapi.Repository.CommentRepository;

import bartlomiejzienkiewicz.forumapi.Entity.Comment.Comment;
import bartlomiejzienkiewicz.forumapi.Repository.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements EntityService<Comment> {

    CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void save(Comment entity) {
        commentRepository.save(entity);
    }

    @Override
    public void deleteAll() {
        commentRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> findCommentsByIdOfPost(Long idOfPost){
        List<Comment> commentList = commentRepository.findAll().stream().
                filter(comment -> comment.getPost().getId().equals(idOfPost)).collect(Collectors.toList());
        return commentList;
    }
    public List<Comment> findCommentsByIdOfCommentThatTheyAreAnsweringTo(Long idOfComment){
        List<Comment> commentList = commentRepository.findAll().stream().
                filter(comment -> comment.getIdOfCommentItAnswers().equals(idOfComment)).collect(Collectors.toList());
        return commentList;
    }
}
