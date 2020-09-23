package internet.store.dao;

import java.util.List;
import java.util.Optional;

public interface GenericGetDao<T,K> {

    Optional<T> get(K itemId);

    List<T> getAll();
}
