package internet.store.service;

import internet.store.model.Product;
import internet.store.model.ShoppingCart;
import java.sql.SQLException;

public interface ShoppingCartService {

    ShoppingCart create(ShoppingCart shoppingCart) throws SQLException;

    ShoppingCart addProduct(ShoppingCart shoppingCart, Product product);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);

    void clear(ShoppingCart shoppingCart);

    ShoppingCart getByUserId(Long userId);

    boolean delete(ShoppingCart shoppingCart);
}
