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
        String query = "SELECT user_id, user_name, login, password, role_id, role_name FROM users "
                + "JOIN users_roles USING (user_id) JOIN roles USING(role_id) "
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
                    + "is not found", ex);
        }
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (user_name, login, password) VALUES (?,?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setUserId(resultSet.getLong(1));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("User " + user.toString()
                    + " was not created", ex);
        }
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            setRoles(user.getUserId(), role.getRoleName().name());
        }
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT user_id, user_name, login, password, role_id, role_name FROM users "
                + "JOIN users_roles USING (user_id) JOIN roles USING(role_id) "
                + "WHERE user_id = ? AND deleted = false;";
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

    @Override
    public List<User> getAll() {
        String query = "SELECT user_id, user_name, login, password, role_name, role_id FROM users "
                + "JOIN users_roles USING (user_id) JOIN roles USING(role_id) "
                + "WHERE deleted = false;";
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userList.add(retrieveDataFromDb(resultSet));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("no users in database", ex);
        }
        return userList;
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET user_name = ?, login = ?,"
                + " password = ? WHERE user_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("User " + user.getUserId() + " not updated", ex);
        }
        return user;
    }

    @Override
    public boolean delete(Long userId) {
        String query = "UPDATE users SET deleted = true WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            return statement.executeUpdate() == 1;
        } catch (SQLException ex) {
            throw new DataProcessingException("User " + userId + " not updated", ex);
        }
    }

    private User retrieveDataFromDb(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong("user_id");
        Set<Role> roleSet = new HashSet<>();
        User user = new User(userId, resultSet.getString("user_name"),
                resultSet.getString("login"), resultSet.getString("password"));
        roleSet.add(new Role(resultSet.getLong("role_id"),
                Role.RoleName.valueOf(resultSet.getString("role_name"))));
        user.setRoles(roleSet);
        return user;
    }

    private boolean setRoles(Long userId, String role) {
        String query = "INSERT INTO users_roles (user_id, role_id) SELECT ?, "
                + "role_id FROM roles WHERE role_name = ?;";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.setString(2, role);
            return statement.executeUpdate() == 1;
        } catch (SQLException ex) {
            throw new DataProcessingException("no users in database", ex);
        }
    }
}
