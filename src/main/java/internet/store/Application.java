package internet.store;

import internet.store.db.Storage;
import internet.store.lib.Injector;
import internet.store.model.Product;
import internet.store.model.ShoppingCart;
import internet.store.model.User;
import internet.store.service.OrderService;
import internet.store.service.ProductService;
import internet.store.service.ShoppingCartService;
import internet.store.service.UserService;
import java.sql.SQLException;
import java.util.List;

public class Application {
    private static Injector injector = Injector.getInstance("internet.store");

    public static void main(String[] args) throws SQLException {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        Product samsung = new Product("Samsung", 2500);
        Product nokia3 = new Product("Nokia 3", 1000);
        productService.create(nokia3);
        productService.create(new Product("Huaway", 1300));
        productService.create(samsung);
        productService.create(new Product("Iphone V", 3000));
        productService.create(new Product("Nokia 5", 1700));

        System.out.println(productService.get(3L).toString());

        List<Product> allProducts = Storage.products;
        for (Product item : allProducts) {
            System.out.println(item.toString());
        }
        samsung.setProductName("Samsung A");
        samsung.setPrice(2550);
        System.out.println(productService.update(samsung).toString());
        System.out.println(productService.delete(samsung.getId()));
        System.out.println(productService.getAll());

        UserService userService = (UserService) injector.getInstance(UserService.class);
        User user = new User("Andy", "an", "12345");
        userService.create(new User("Mary", "ma", "54321"));
        userService.create(new User("Dan", "Smart", "cjnwuU@#!129"));
        userService.create(user);

        System.out.println(userService.get(3L));
        System.out.println(userService.get(1L));
        user.setLogin("anytime");
        userService.update(user);
        userService.delete(1L);
        System.out.println(userService.getAll().toString());

        ShoppingCartService shoppingCartService = (ShoppingCartService) injector
                .getInstance(ShoppingCartService.class);
        ShoppingCart danShopCart = new ShoppingCart(2L);
        shoppingCartService.create(danShopCart);
        ShoppingCart andyShopCart = new ShoppingCart(3L);
        shoppingCartService.create(andyShopCart);

        shoppingCartService.addProduct(andyShopCart, samsung);
        shoppingCartService.addProduct(andyShopCart, nokia3);
        shoppingCartService.addProduct(danShopCart, nokia3);

        System.out.println(shoppingCartService.getByUserId(3L));
        System.out.println("Nokia 3 was deleted from Andy's shopping cart - "
                + shoppingCartService.deleteProduct(andyShopCart, nokia3));
        System.out.println(shoppingCartService.getByUserId(3L));

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        orderService.completeOrder(andyShopCart);
        orderService.completeOrder(danShopCart);
        System.out.println(orderService.getUserOrders(3L));
        System.out.println("Empty cart - " + shoppingCartService.getByUserId(3L));
        System.out.println(orderService.get(1L));
        System.out.println(orderService.getAll());
    }
}
