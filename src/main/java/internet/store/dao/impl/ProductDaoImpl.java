package internet.store.dao.impl;

import internet.store.dao.ProductDao;
import internet.store.db.Storage;
import internet.store.lib.Dao;
import internet.store.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class ProductDaoImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        Storage.addProduct(product);
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Storage.products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return Storage.products;
    }

    @Override
    public Product update(Product product) {
        IntStream.range(0, Storage.products.size())
                .filter(p -> product.getId().equals(Storage.products.get(p).getId()))
                .forEach(p -> Storage.products.set(p, product));
        return product;
    }

    @Override
    public boolean delete(Product product) {
        return Storage.products
                .removeIf(p -> product.getId().equals(p.getId()));
    }
}
