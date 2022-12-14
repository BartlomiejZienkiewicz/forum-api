package bartlomiejzienkiewicz.forumapi.Repository.UserRepository;

import bartlomiejzienkiewicz.forumapi.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
