package internet.store.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long orderId;
    private List<Product> products;
    private Long userId;

    public Order(List<Product> products, Long userId) {
        this.products = new ArrayList<>();
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}