package bartlomiejzienkiewicz.forumapi.Repository;

import java.util.Optional;

public interface EntityService<T> {
    void save(T entity);

    void deleteAll();
    void deleteById(Long id);

    Optional<T> findById(Long id);
}
