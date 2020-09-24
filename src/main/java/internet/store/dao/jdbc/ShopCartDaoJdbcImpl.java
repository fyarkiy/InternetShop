package internet.store.dao.jdbc;

import internet.store.dao.ShopCartDao;
import internet.store.exception.DataProcessingException;
import internet.store.lib.Dao;
import internet.store.model.Product;
import internet.store.model.ShoppingCart;
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
public class ShopCartDaoJdbcImpl implements ShopCartDao {
    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        ShoppingCart shoppingCart = getShoppingCart(userId);
        shoppingCart.setProducts(getProductsFromCart(shoppingCart.getCartId()));
    return Optional.ofNullable(shoppingCart);
    }

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        String query = "INSERT INTO shopping_cart (user_id) values (?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, shoppingCart.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                shoppingCart.setCartId(resultSet.getLong(1));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Shopping cart "
                    + shoppingCart + " was not created", ex);
        }
        return shoppingCart;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        deleteProductsFromCart(shoppingCart.getCartId());
        for (Product product : shoppingCart.getProducts()) {
            addProductToCart(shoppingCart.getCartId(), product.getId());
        }
        return shoppingCart;
    }

    @Override
    public boolean delete(Long cartId) {
        String query = "UPDATE shopping_cart SET deleted = true WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cartId);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Shopping cart for card ID "
                    + cartId + " was not deleted", ex);
        }
    }

    private boolean deleteProductsFromCart(Long cartId) {
        String queryDelete = "DELETE FROM shopping_cart_product WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(queryDelete)) {
            statement.setLong(1, cartId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("products from cart "
                    + cartId + " were not deleted", ex);
        }
        return true;
    }

    private boolean addProductToCart(Long cartId, Long productId) {
        String query = "INSERT INTO shopping_cart_product (cart_id, product_id) VALUES (?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cartId);
            statement.setLong(2, productId);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Shopping cart " + cartId + " was not updated", ex);
        }
    }

    private Product retrieveProductDataFromDB(ResultSet resultSet) throws SQLException {
        Product product = new Product(resultSet.getLong("id"),
                resultSet.getString("product_name"), resultSet.getDouble("price"));
        return product;
    }

    private ShoppingCart getShoppingCart(Long userId) {
        String query = "SELECT cart_id FROM shopping_cart "
                + "WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ShoppingCart shoppingCart = new ShoppingCart(userId);
            if (resultSet.next()) {
                shoppingCart.setCartId(resultSet.getLong("cart_id"));
            }
            return shoppingCart;
        } catch (SQLException ex) {
            throw new DataProcessingException("Shopping cart for user "
                    + userId + " not found", ex);
        }
    }

    private List<Product> getProductsFromCart(Long cartId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT id, product_name, price FROM shopping_cart_product scp "
                + "JOIN products p ON p.id = scp.product_id "
                + "WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cartId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
//            Long productId = resultSet.getObject("id", Long.class);
//            if (productId != null) {
                products.add(retrieveProductDataFromDB(resultSet));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("can't get products from shopping cart "
                    + cartId, ex);
        }
        return products;
    }
}
