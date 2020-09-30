package internet.store.dao.jdbc;

import internet.store.dao.UserDao;
import internet.store.exception.DataProcessingException;
import internet.store.lib.Dao;
import internet.store.model.Role;
import internet.store.model.User;
import internet.store.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {

        Optional<User> user = getUserByLogin(login);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        user.get().setRoles(getUserRoles(user.get().getUserId()));
        return user;
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (user_name, login, password, salt) VALUES (?,?,?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setBytes(4, user.getSalt());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setUserId(resultSet.getLong(1));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("User " + user.toString()
                    + " was not created", ex);
        }
        defineRoles(user);
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        Optional<User> user = getUserById(id);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        user.get().setRoles(getUserRoles(id));
        return user;
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT user_id, user_name, login, password, salt FROM users "
                + "WHERE deleted = false;";
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(retrieveDataFromDb(resultSet));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("no users in database", ex);
        }
        for (User user : users) {
            user.setRoles(getUserRoles(user.getUserId()));
        }
        return users;
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET user_name = ?, login = ?,"
                + " password = ?, salt = ? WHERE user_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setBytes(4, user.getSalt());
            statement.setLong(5, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("User " + user.getUserId() + " not updated", ex);
        }
        removeRoles(user.getUserId());
        defineRoles(user);
        return user;
    }

    @Override
    public boolean delete(Long userId) {
        removeRoles(userId);
        String query = "UPDATE users SET deleted = true WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("User " + userId + " not updated", ex);
        }

    }

    private Optional<User> getUserByLogin(String login) {
        String query = "SELECT user_id, user_name, login, password, salt FROM users "
                + "WHERE login = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(retrieveDataFromDb(resultSet));
            }
            return Optional.empty();
        } catch (SQLException ex) {
            throw new DataProcessingException("User with login " + login
                    + " is not found", ex);
        }
    }

    private Optional<User> getUserById(Long id) {
        String query = "SELECT user_id, user_name, login, password, salt FROM users "
                + " WHERE user_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(retrieveDataFromDb(resultSet));
            }
            return Optional.empty();
        } catch (SQLException ex) {
            throw new DataProcessingException("User with id " + id
                    + " is not found", ex);
        }
    }

    private User retrieveDataFromDb(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong("user_id");
        return new User(userId, resultSet.getString("user_name"),
                resultSet.getString("login"), resultSet.getString("password"),
                resultSet.getBytes("salt"));
    }

    private Set<Role> getUserRoles(Long userId) {
        String query = "SELECT role_name, role_id FROM roles "
                + " JOIN users_roles USING (role_id) WHERE user_id = ?;";
        Set<Role> roles = new HashSet<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roles.add(new Role(resultSet.getLong("role_id"),
                        Role.RoleName.valueOf(resultSet.getString("role_name"))));
            }
            return roles;
        } catch (SQLException ex) {
            throw new DataProcessingException("no users in database", ex);
        }
    }

    private boolean defineRoles(User user) {
        String query = "INSERT INTO users_roles (user_id, role_id) SELECT ?, "
                    + " role_id FROM roles WHERE role_name = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
            int updates = 0;
            for (Role role : user.getRoles()) {
                statement.setLong(1, user.getUserId());
                statement.setString(2, role.getRoleName().name());
                updates += statement.executeUpdate();
            }
            return updates > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("user " + user.getUserId()
                        + " was not updated", ex);
        }
    }

    private boolean removeRoles(Long userId) {
        String query = "DELETE FROM users_roles WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            int updates = statement.executeUpdate();
            return updates > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("roles for user " + userId
                    + " were not updated", ex);
        }
    }
}
