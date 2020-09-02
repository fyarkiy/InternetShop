package internet.store.service;

import internet.store.dao.ProductDao;
import internet.store.dao.ProductDaoImpl;

public class Factory {
    private static ProductDao productDao;

    public static ProductDao getProductDao() {
        if (productDao == null) {
            productDao = new ProductDaoImpl();
        }
        return productDao;
    }
}
