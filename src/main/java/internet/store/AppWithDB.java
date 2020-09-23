package internet.store;

import internet.store.lib.Injector;
import internet.store.model.Product;
import internet.store.model.Role;
import internet.store.model.ShoppingCart;
import internet.store.model.User;
import internet.store.service.ProductService;
import internet.store.service.ShoppingCartService;
import internet.store.service.UserService;
import org.w3c.dom.ls.LSOutput;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AppWithDB {
    private static Injector injector = Injector.getInstance("internet.store");

    public static void main(String[] args) throws SQLException {
        ProductService productService = (ProductService) injector
                .getInstance(ProductService.class);
        Product apple = new Product(24L, "apple", 150);
        Product pear = new Product(2L, "pear", 134);
  /*      productService.create(apple);
        productService.update(pear);

        System.out.println(productService.delete(3L));
        List<Product> products = productService.getAll();
        for (Product product : products) {
            System.out.println(product.toString());
        }

        UserService userService = (UserService) injector
                .getInstance(UserService.class);
        User sam = new User("sam", "sam", "123");
        sam.setRoles(Set.of(Role.of("USER")));

        userService.create(sam);
        System.out.println(userService.get(1L).toString());

        sam.setUserName("Sam Jr");
        sam.setUserId(10L);
        userService.update(sam);
        System.out.println(userService.findByLogin("sam"));
        userService.delete(7L);
        for (User user : userService.getAll()) {
            System.out.println(user.toString());
            for (Role role : user.getRoles()) {
                System.out.println(role.toString());
            }
        }
  */
        ShoppingCartService shoppingCartService = (ShoppingCartService) injector
                .getInstance(ShoppingCartService.class);
        System.out.println(shoppingCartService.getByUserId(1L).getCartId());
/*       System.out.println(shoppingCartService.create(new ShoppingCart(10L)).toString());
        List<Product> products = new ArrayList<>();
        products.add(pear);
        products.add(apple);


        ShoppingCart cart = shoppingCartService.getByUserId(1L);
       cart.setProducts(products);
       shoppingCartService.addProduct(cart,pear);
        cart = shoppingCartService.getByUserId(1L);
        shoppingCartService.addProduct(cart, apple);
        cart = shoppingCartService.getByUserId(1L);
        shoppingCartService.deleteProduct(cart, apple);
        cart = shoppingCartService.getByUserId(1L);
        for (Product product : cart.getProducts()) {
            System.out.println(product.toString());
        }
 */
        ShoppingCart cart = new ShoppingCart(3L);
        cart.setCartId(4L);
        shoppingCartService.delete(cart);
    }
}
