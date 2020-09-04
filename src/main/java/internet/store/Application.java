package internet.store;

import internet.store.db.Storage;
import internet.store.lib.Injector;
import internet.store.model.Product;
import internet.store.service.ProductService;
import java.util.List;

public class Application {
    private static Injector injector = Injector.getInstance("internet.store");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        Product product = new Product("Samsung", 2500);
        productService.create(new Product("Nokia 3", 1000));
        productService.create(new Product("Huaway", 1300));
        productService.create(product);
        productService.create(new Product("Iphone V", 3000));
        productService.create(new Product("Nokia 5", 1700));

        System.out.println(productService.get(3L).toString());

        List<Product> allProducts = Storage.products;
        for (Product item : allProducts) {
            System.out.println(item.toString());
        }
        product.setProductName("Samsung A");
        product.setPrice(2550);
        System.out.println(productService.update(product).toString());

        System.out.println(productService.delete(product));

        System.out.println(productService.getAll());
    }
}
