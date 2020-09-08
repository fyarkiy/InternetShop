package internet.store.dao;

import internet.store.model.ShoppingCart;
import java.util.Optional;

public interface ShopCartDao {

    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart update(ShoppingCart shoppingCart);

    Optional<ShoppingCart> getByUserId(Long userID);

    boolean deleteCart(ShoppingCart shoppingCart);
}
