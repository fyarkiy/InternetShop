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
import java.util.Optional;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products (product_name, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getProductName());
            statement.setDouble(2, product.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                product.setId(resultSet.getLong(1));
            }
            return product;
        } catch (SQLException ex) {
            throw new DataProcessingException("Product "
                    + product.toString() + " was not created", ex);
        }
    }

    @Override
    public Optional<Product> get(Long itemId) {
        String query = "SELECT * FROM products WHERE id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, itemId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(retrieveDataFromDB(resultSet));
            }
            return Optional.empty();
        } catch (SQLException ex) {
            throw new DataProcessingException("No such product with id " + itemId, ex);
        }
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products WHERE deleted = false;";
        List<Product> productList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(retrieveDataFromDB(resultSet));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("There are no products in DB", ex);
        }
        return productList;
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET product_name = ?, price = ? "
                + "WHERE id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getProductName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(3, product.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("Not able to update " + product.toString(), ex);
        }
        return product;
    }

    @Override
    public boolean delete(Long productId) {
        String query = "UPDATE products SET deleted = true WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, productId);
            int updates = statement.executeUpdate();
            return  updates > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Delete of product with id = "
                    + productId + "is failed", ex);
        }
    }

    private Product retrieveDataFromDB(ResultSet resultSet) throws SQLException {
        Product product = new Product(resultSet.getLong("id"),
                resultSet.getString("product_name"), resultSet.getDouble("price"));
        return product;
    }
}
