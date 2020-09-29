package internet.store.dao.jdbc;

import internet.store.dao.OrderDao;
import internet.store.exception.DataProcessingException;
import internet.store.lib.Dao;
import internet.store.model.Order;
import internet.store.model.Product;
import internet.store.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {

    @Override
    public List<Order> getUserOrders(Long userId) {
        String query = "SELECT order_id FROM orders WHERE deleted = false AND user_id = ?;";
        List<Long> ordersId = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                orders.add(get(resultSet.getLong("order_id")).get());
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("there are no orders", ex);
        }
        return orders;
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT order_id FROM orders WHERE deleted = false;";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(get(resultSet.getLong("order_id")).get());
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("there are no orders", ex);
        }
        return orders;
    }

    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders (user_id) values (?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                order.setOrderId(resultSet.getLong(1));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Order " + order + " was not created", ex);
        }
        return addProductsToOrder(order);
    }

    @Override
    public Order update(Order order) {
        deleteProductsFromOrder(order.getOrderId());
        return addProductsToOrder(order);
    }

    @Override
    public boolean delete(Long orderId) {
        deleteProductsFromOrder(orderId);
        String query = "UPDATE orders SET deleted = true WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            int updates = statement.executeUpdate();
            return updates > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Order # " + orderId + " was not deleted", ex);
        }
    }

    @Override
    public Optional<Order> get(Long orderId) {
        String query = "SELECT order_id, user_id, product_id, product_name, price FROM orders"
                + " JOIN orders_products USING(order_id)"
                + " JOIN products ON (product_id = products.id)"
                + " WHERE order_id = ? AND orders.deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long prodId = resultSet.getLong("product_id");
                Long userId = resultSet.getLong("user_id");
                Order order = new Order(userId);
                order.setOrderId(orderId);
                order.setProducts(retrieveProductData(resultSet));
                return Optional.of(order);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("No order # " + orderId + " found", ex);
        }
        return Optional.empty();
    }

    private boolean deleteProductsFromOrder(Long orderId) {
        String query = "DELETE FROM orders_products WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("products from order " + orderId
                    + " were not deleted", ex);
        }
    }

    private Order addProductsToOrder(Order order) {
        String query = "INSERT INTO orders_products (product_id, order_id) values (?, ?);";
        for (Product product : order.getProducts()) {
            try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement statement = connection.prepareStatement(query,
                            Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, product.getId());
                statement.setLong(2, order.getOrderId());
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new DataProcessingException("Order " + order.getOrderId()
                        + " was not created", ex);
            }
        }
        return order;
    }

    private List<Product> retrieveProductData(ResultSet resultSet) throws SQLException {
        List<Product> products = new ArrayList<>();
        do {
            products.add(new Product(resultSet.getLong("product_id"),
                    resultSet.getString("product_name"), resultSet.getDouble("price")));
        } while (resultSet.next());
        return products;
    }
}
