package internet.store.service;

public interface GenericCreateUpdateService<T> {
    T create(T item);

    T update(T item);
}
