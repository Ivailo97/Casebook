package app.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<E, I> {

    E save(E entity);

    E findById(I id);

    List<E> findAll();

    Long count();
}
