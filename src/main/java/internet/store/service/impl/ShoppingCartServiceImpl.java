package internet.store.service.impl;

import internet.store.dao.ShopCartDao;
import internet.store.lib.Inject;
import internet.store.lib.Service;
import internet.store.model.Product;
import internet.store.model.ShoppingCart;
import internet.store.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShopCartDao shopCartDao;

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return shopCartDao.create(shoppingCart);
    }

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.getProducts().add(product);
        return shopCartDao.update(shoppingCart);
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        boolean result = shoppingCart.getProducts().remove(product);
        shopCartDao.update(shoppingCart);
        return result;
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getProducts().clear();
        shopCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return shopCartDao.getByUserId(userId).get();
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        return shopCartDao.deleteCart(shoppingCart);
    }
}
