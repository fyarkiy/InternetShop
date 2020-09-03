package internet.store.service.impl;

import internet.store.dao.ProductDao;
import internet.store.lib.Inject;
import internet.store.lib.Service;
import internet.store.model.Product;
import internet.store.service.Factory;
import internet.store.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Inject
    private ProductDao productDao;

    @Override
    public Product create(Product product) {
        return Factory.getProductDao().create(product);
    }

    @Override
    public Optional<Product> get(Long id) {
        return Factory.getProductDao().get(id);
    }

    @Override
    public List<Product> getAll() {
        return Factory.getProductDao().getAll();
    }

    @Override
    public Product update(Product product) {
        return Factory.getProductDao().update(product);
    }

    @Override
    public boolean delete(Product product) {
        return Factory.getProductDao().delete(product);
    }
}
