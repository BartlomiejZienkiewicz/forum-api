package bartlomiejzienkiewicz.forumapi.Repository.CommentRepository;

import bartlomiejzienkiewicz.forumapi.Entity.Comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment ,Long> {
}
