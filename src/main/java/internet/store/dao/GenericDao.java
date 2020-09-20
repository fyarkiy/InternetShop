package internet.store.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {
    T create(T item) throws SQLException;

    Optional<T> get(K itemId);

    List<T> getAll();

    T update(T item);

    boolean delete(K itemId);
}
