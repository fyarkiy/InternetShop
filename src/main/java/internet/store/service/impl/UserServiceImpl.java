package internet.store.service.impl;

import internet.store.dao.UserDao;
import internet.store.lib.Inject;
import internet.store.lib.Service;
import internet.store.model.User;
import internet.store.service.UserService;
import internet.store.util.HashUtil;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User create(User user) {
        byte[] salt = HashUtil.getSalt();
        user.setSalt(salt);
        user.setPassword(HashUtil.hashPassword(user.getPassword(), salt));
        return userDao.create(user);
    }

    @Override
    public User get(Long id) {
        return userDao.get(id).get();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    @Override
    public Optional<User> findByLogin(String logIn) {
        return userDao.findByLogin(logIn);
    }
}
