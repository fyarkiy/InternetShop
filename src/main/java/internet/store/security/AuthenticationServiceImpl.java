package internet.store.security;

import internet.store.exception.AuthenticationException;
import internet.store.lib.Inject;
import internet.store.lib.Service;
import internet.store.model.User;
import internet.store.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        return userService.findByLogin(login)
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new AuthenticationException("Incorrect username or password"));
    }
}
