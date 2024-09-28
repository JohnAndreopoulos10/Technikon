package gr.ed.technikon.Repositories;

import gr.ed.technikon.exceptions.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;

public interface OwnerRepositoryInterface<T, K, S> {

    Optional<T> findByOwnerId(K id);

    Optional<T> findByVatNumber(K vt);

    Optional<T> findByEmail(S s);

    List<T> findAll() throws ResourceNotFoundException;

    Optional<T> save(T t);

    boolean deleteById(K id);

    boolean safeDeleteById(K id);

    Optional<T> update(T t);

}
