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
        String query = "SELECT user_id, cart_id, id, product_name, price FROM users "
                + "JOIN shopping_cart using(user_id) "
                + "LEFT JOIN shopping_cart_product USING(cart_id) "
                + "LEFT JOIN products ON products.id = shopping_cart_product.product_id "
                + "WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ShoppingCart shoppingCart = new ShoppingCart(userId);
            List<Product> productList = new ArrayList<>();
            if (resultSet.next()) {
                shoppingCart.setCartId(resultSet.getLong("cart_id"));
            }
            do {
                Long productId = resultSet.getObject("id", Long.class);
                if (productId != null) {
                    productList.add(retrieveProductDataFromDB(resultSet));
                }
            } while (resultSet.next());
            shoppingCart.setProducts(productList);
            return Optional.of(shoppingCart);
        } catch (SQLException ex) {
            throw new DataProcessingException("Shopping cart for user " + userId + " not found", ex);
        }
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
            throw new DataProcessingException("Shopping cart " + shoppingCart + " was not created", ex);
        }
        return shoppingCart;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        deleteProductsFromCart(shoppingCart.getCartId());
        String query = "INSERT INTO shopping_cart_product (cart_id, product_id) values (?,?);";
        for (Product product : shoppingCart.getProducts()) {
            try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, shoppingCart.getCartId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new DataProcessingException("Shopping cart " + shoppingCart + " was not updated", ex);
            }
        }
        return shoppingCart;
    }

    @Override
    public boolean delete(Long cartId) {
        String query = "UPDATE shopping_cart SET deleted = true WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cartId);
            return statement.executeUpdate() == 1;
        } catch (SQLException ex) {
            throw new DataProcessingException("Shopping cart for card ID " + cartId + " was not deleted", ex);
        }
    }

    private boolean deleteProductsFromCart(Long cartId) {
        String queryDelete = "DELETE FROM shopping_cart_product WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryDelete)) {
            statement.setLong(1, cartId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("no products from cart " + cartId + " was not deleted", ex);
        }
        return true;
    }

    private Product retrieveProductDataFromDB(ResultSet resultSet) throws SQLException {
        Product product = new Product(resultSet.getLong("id"),
                resultSet.getString("product_name"), resultSet.getDouble("price"));
        return product;
    }
 /*   @Override
    public Optional<ShoppingCart> get(Long cartId) {
        String query = "SELECT user_id, cart_id, id, product_name, price FROM shopping_cart "
                + "JOIN shopping_cart_product USING(cart_id) "
                + "JOIN products ON products.id = shopping_cart_product.product_id "
                + "WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cartId);
            ResultSet resultSet = statement.executeQuery();
            List<Product> productList = new ArrayList<>();
            ShoppingCart shoppingCart = new ShoppingCart(resultSet.getLong("user_id"));
            while (resultSet.next()) {
                shoppingCart.setCartId(cartId);
                productList.add(new Product(resultSet.getLong("id"),
                        resultSet.getString("product_name"), resultSet.getDouble("price")));
            }
            shoppingCart.setProducts(productList);
            return Optional.of(shoppingCart);
        } catch (SQLException ex) {
            throw new DataProcessingException("Shopping cart # " + cartId + " not found", ex);
        }
    }

     public List<ShoppingCart> getAll() {
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        String query = "SELECT user_id, cart_id FROM shopping_cart;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                ShoppingCart shoppingCart = new ShoppingCart(resultSet.getLong("user_id"));
                shoppingCart.setCartId(resultSet.getLong("cart_id"));
                shoppingCarts.add(shoppingCart);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get all Shopping carts", ex);
        }
        for (ShoppingCart shopCart : shoppingCarts ) {
            shopCart.setProducts(getProductsFromCart(shopCart.getCartId()));
        }
        return shoppingCarts;
    }
     */

    private List<Product> getProductsFromCart(Long cartId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT id, product_name, price FROM shopping_cart_product "
                + "JOIN products ON products.id = shopping_cart_product.product_id "
                + "WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cartId);
            ResultSet resultSet = statement.executeQuery();
            Long productId = resultSet.getObject("id", Long.class);
            if (productId != null) {
                products.add(retrieveProductDataFromDB(resultSet));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("can't get products from shopping cart " + cartId, ex);
        }
        return products;
    }
}
