package internet.store.dao.impl;

import internet.store.dao.UserDao;
import internet.store.db.Storage;
import internet.store.lib.Dao;
import internet.store.model.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class UserDaoImpl implements UserDao {

    @Override
    public User create(User user) {
        Storage.addUser(user);
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        return Storage.users.stream()
                .filter(u -> u.getUserId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public User update(User user) {
        IntStream.range(0, Storage.users.size())
                .filter(u -> user.getUserId().equals(Storage.users.get(u).getUserId()))
                .forEach(id -> Storage.users.set(id, user));
        return user;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.users.removeIf(u -> u.getUserId().equals(id));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Storage.users.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
    }
}
