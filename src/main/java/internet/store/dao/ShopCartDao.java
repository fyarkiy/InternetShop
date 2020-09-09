package internet.store.dao;

import internet.store.model.ShoppingCart;
import java.util.Optional;

public interface ShopCartDao extends GenericDao<ShoppingCart, Long> {
    public Optional<ShoppingCart> getByUserId(Long userId);

}
