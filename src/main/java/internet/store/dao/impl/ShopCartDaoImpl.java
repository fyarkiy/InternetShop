package internet.store.dao.impl;

import internet.store.dao.ShopCartDao;
import internet.store.db.Storage;
import internet.store.model.ShoppingCart;
import java.util.Optional;
import java.util.stream.IntStream;

public class ShopCartDaoImpl implements ShopCartDao {

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.addCart(shoppingCart);
        return shoppingCart;
    }

    public Optional<ShoppingCart> get(Long id) {
        return Storage.shopCarts.stream()
                .filter(s -> s.getCartId().equals(id))
                .findFirst();
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        IntStream.range(0, Storage.shopCarts.size())
                .filter(p -> shoppingCart.getCartId()
                        .equals(Storage.shopCarts.get(p).getCartId()))
                .forEach(p -> Storage.shopCarts.set(p, shoppingCart));
        return shoppingCart;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.shopCarts.removeIf(s -> s.getCartId().equals(id));
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        return Storage.shopCarts.stream()
                .filter(s -> s.getUserId().equals(userId))
                .findFirst();
    }
}
