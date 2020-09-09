package internet.store.service;

import internet.store.model.Product;

public interface ProductService extends GenericService<Product, Long>,
        GenericCreateUpdateService<Product> {
}
