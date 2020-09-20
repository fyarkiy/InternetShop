package internet.store.dao.jdbc;

import internet.store.dao.ProductDao;
import internet.store.exception.DataProcessingException;
import internet.store.lib.Dao;
import internet.store.model.Product;
import internet.store.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {

    @Override
    public Product create(Product item) {
        String query = "INSERT INTO products (product_name, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getProductName());
            statement.setDouble(2, item.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                item.setId(resultSet.getLong(1));
            }
            return item;
        } catch (SQLException ex) {
            throw new DataProcessingException("Product "
                    + item.toString() + " was not created", ex);
        }
    }

    @Override
    public Optional<Product> get(Long itemId) {
        String query = "SELECT * FROM products WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, itemId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            if (resultSet.getLong("deleted") == 0) {
                return Optional.of(retrieveDataFromDB(resultSet));
            }
            throw new NoSuchElementException("Product with id = "
                    + itemId + " was marked for deletion");
        } catch (SQLException ex) {
            throw new DataProcessingException("No such product with id " + itemId, ex);
        }
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products;";
        List<Product> productList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getLong("deleted") == 0) {
                    productList.add(retrieveDataFromDB(resultSet));
                }
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("There are no products in DB", ex);
        }
        return productList;
    }

    @Override
    public Product update(Product item) {
        String query = "UPDATE products SET product_name = ?, price = ? where product_name = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, item.getProductName());
            statement.setDouble(2, item.getPrice());
            statement.setString(3, item.getProductName());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("Not able to update " + item.toString(), ex);
        }
        return item;
    }

    @Override
    public boolean delete(Long itemId) {
        String query = "UPDATE products SET deleted = 1 where id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, itemId);
            return statement.executeUpdate() == 1;
        } catch (SQLException ex) {
            throw new RuntimeException("Delete of product with id = " + itemId + "is failed", ex);
        }
    }

    private static Product retrieveDataFromDB(ResultSet resultSet) throws SQLException {
        Product product = new Product(resultSet.getString("product_name"),
                resultSet.getDouble("price"));
        product.setId(resultSet.getLong("id"));
        return product;
    }
}
