package internet.store.service;

import internet.store.model.User;
import java.util.Optional;

public interface UserService extends GenericService<User, Long>,
        GenericCreateUpdateService<User> {

    Optional<User> findByLogin(String logIn);
}
