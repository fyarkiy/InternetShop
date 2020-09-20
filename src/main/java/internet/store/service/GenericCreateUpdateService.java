package internet.store.service;

import java.sql.SQLException;

public interface GenericCreateUpdateService<T> {
    T create(T item) throws SQLException;

    T update(T item);
}
