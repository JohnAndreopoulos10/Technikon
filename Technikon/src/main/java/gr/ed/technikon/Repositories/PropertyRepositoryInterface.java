package gr.ed.technikon.Repositories;

import gr.ed.technikon.exceptions.CustomException;
import java.util.List;
import java.util.Optional;

public interface PropertyRepositoryInterface<T, K> {

    Optional<T> findById(K id);

    List<T> findByOwnerId(K ownerId);

    List<T> findAll();

    Optional<T> save(T property);

    boolean deleteById(K id);

    boolean safeDeleteById(K id);

    Optional<T> update(T entity);
}
