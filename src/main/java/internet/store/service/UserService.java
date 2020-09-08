package internet.store.service;

import internet.store.model.User;

public interface UserService extends GenericService<User, Long, Long>,
        GenericCreateUpdateService<User> {
}
