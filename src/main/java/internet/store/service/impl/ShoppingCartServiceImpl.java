package internet.store.service.impl;

import internet.store.dao.ShopCartDao;
import internet.store.lib.Inject;
import internet.store.lib.Service;
import internet.store.model.Product;
import internet.store.model.ShoppingCart;
import internet.store.service.ShoppingCartService;
import java.sql.SQLException;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShopCartDao shopCartDao;

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) throws SQLException {
        return shopCartDao.create(shoppingCart);
    }

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.getProducts().add(product);
        return shopCartDao.update(shoppingCart);
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.getProducts().remove(product);
        shopCartDao.update(shoppingCart);
        return !shoppingCart.getProducts().removeIf(p -> p.equals(product));
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getProducts().clear();
        shopCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUserId(Long userID) {
        return shopCartDao.getByUserId(userID).get();
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        return shopCartDao.delete(shoppingCart.getCartId());
    }
}
