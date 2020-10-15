package internet.store.db;

import internet.store.model.Order;
import internet.store.model.Product;
import internet.store.model.ShoppingCart;
import internet.store.model.User;
import java.util.ArrayList;
import java.util.List;


public class Storage {
    public static final List<Order> orders = new ArrayList<>();
    public static final List<Product> products = new ArrayList<>();
    public static final List<ShoppingCart> shopCarts = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    private static long productId = 0;
    private static long orderId = 0;
    private static long cartId = 0;
    private static long userId = 0;

    public static void addProduct(Product product) {
        productId++;
        product.setId(productId);
        products.add(product);
    }

    public static void addOrder(Order order) {
        orderId++;
        order.setOrderId(orderId);
        orders.add(order);
    }

    public static void addCart(ShoppingCart shoppingCart) {
        cartId++;
        shoppingCart.setCartId(cartId);
        shopCarts.add(shoppingCart);
    }

    public static void addUser(User user) {
        userId++;
        user.setUserId(userId);
        users.add(user);
    }
}
