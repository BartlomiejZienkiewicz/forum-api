package bartlomiejzienkiewicz.forumapi.Dao;

import bartlomiejzienkiewicz.forumapi.Dao.Post.PostDao;

public interface Dao<T> {

    void convertToDao(T entity);


}
