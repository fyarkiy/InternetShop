package internet.store.service;

import java.util.List;

public interface GenericService<T, K, M> {

    T get(K id);

    List<T> getAll();

    boolean delete(M item);
}
