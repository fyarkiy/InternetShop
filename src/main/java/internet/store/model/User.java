package internet.store.model;

import java.util.Objects;
import java.util.Set;

public class User {
    private Long userId;
    private String userName;
    private String login;
    private String password;
    private byte[] salt;
    private Set<Role> roles;

    public User(String userName, String login, String password) {
        this.userName = userName;
        this.login = login;
        this.password = password;
    }

    public User(Long userId, String userName, String login, String password, byte[] salt) {
        this.userId = userId;
        this.userName = userName;
        this.login = login;
        this.password = password;
        this.salt = salt;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(getUserId(), user.getUserId())
                && Objects.equals(getUserName(), user.getUserName())
                && Objects.equals(getLogin(), user.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUserName(), getLogin());
    }
}
