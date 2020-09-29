package internet.store.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private Long orderId;
    private List<Product> products;
    private Long userId;

    public Order(Long userId) {
        this.userId = userId;
        products = new ArrayList<>();
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

    @Override
    public String toString() {
        return "Order{orderId="
                + orderId
                + ", products="
                + products
                + ", userId="
                + userId
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(getOrderId(), order.getOrderId())
                && Objects.equals(getUserId(), order.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getUserId());
    }
}
