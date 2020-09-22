package internet.store.model;

import java.util.Set;

public class User {
    private Long userId;
    private String userName;
    private String login;
    private String password;
    private Set<Role> roles;

    public User(String userName, String login, String password) {
        this.userName = userName;
        this.login = login;
        this.password = password;
    }

    public User(Long userId, String userName, String login, String password) {
        this.userId = userId;
        this.userName = userName;
        this.login = login;
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{ userName='"
                + userName
                + '\''
                + ", login='"
                + login
                + '\''
                + '}';
    }
}
