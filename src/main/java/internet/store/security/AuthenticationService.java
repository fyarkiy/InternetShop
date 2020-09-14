package internet.store.security;

import internet.store.exception.AuthenticationException;
import internet.store.model.User;

public interface AuthenticationService {
    User login(String login, String password) throws AuthenticationException;
}
