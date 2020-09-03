package internet.store.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private Long cartId;
    private List<Product> products;
    private Long userId;

    public ShoppingCart(List<Product> products, Long userId) {
        this.products = new ArrayList<>();
        this.userId = userId;
    }

    public Long getCartId() {
        return cartId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Long getUserId() {
        return userId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}