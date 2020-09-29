package internet.store.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShoppingCart {
    private Long cartId;
    private List<Product> products;
    private Long userId;

    public ShoppingCart(Long userId) {
        this.userId = userId;
        products = new ArrayList<>();
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

    @Override
    public String toString() {
        return "ShoppingCart{ cartId="
                + cartId
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
        if (!(o instanceof ShoppingCart)) {
            return false;
        }
        ShoppingCart that = (ShoppingCart) o;
        return getCartId().equals(that.getCartId())
                && getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCartId(), getUserId());
    }
}
