package bartlomiejzienkiewicz.forumapi.Dao.User;

import bartlomiejzienkiewicz.forumapi.Dao.Dao;
import bartlomiejzienkiewicz.forumapi.Entity.User.User;

public class UserProfileDao implements Dao<User> {

    String username;

    @Override
    public void convertToDao(User entity) {
        this.username = entity.getUsername();

    }
}
