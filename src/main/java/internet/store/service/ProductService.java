package internet.store.service;

import internet.store.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    public Product create(Product product);

    public Optional<Product> get(Long id);

    public List<Product> getAll();

    public Product update(Product product);

    public boolean delete(Product product);
}
