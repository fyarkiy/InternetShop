package internet.store.dao;

public interface GenericDao<T, K> {
    T create(T item);

    T update(T item);

    boolean delete(K itemId);
}
