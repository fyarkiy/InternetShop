package internet.store;

import internet.store.lib.Injector;
import internet.store.model.Product;
import internet.store.service.ProductService;
import java.sql.SQLException;
import java.util.List;

public class AppWithDB {
    private static Injector injector = Injector.getInstance("internet.store");

    public static void main(String[] args) throws SQLException {
        ProductService productService = (ProductService) injector
                .getInstance(ProductService.class);
        Product apple = new Product("apple", 150);
        productService.create(apple);
        Product pear = new Product("pear", 185);
        productService.update(pear);

        System.out.println(productService.delete(3L));
        List<Product> products = productService.getAll();
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }
}
