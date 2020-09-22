package internet.store;

import internet.store.lib.Injector;
import internet.store.model.Product;
import internet.store.model.Role;
import internet.store.model.User;
import internet.store.service.ProductService;
import internet.store.service.UserService;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class AppWithDB {
    private static Injector injector = Injector.getInstance("internet.store");

    public static void main(String[] args) throws SQLException {
        ProductService productService = (ProductService) injector
                .getInstance(ProductService.class);
        Product apple = new Product("apple", 150);
        Product pear = new Product(2L, "pear", 134);
        productService.create(apple);
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
    }
}
