package internet.store.service;

import internet.store.model.Order;
import internet.store.model.ShoppingCart;
import java.util.List;

public interface OrderService {

    Order completeOrder(ShoppingCart shoppingCart);

    List<Order> getUserOrders(Long userId);

    Order get(Long id);

    List<Order> getAll();

    boolean delete(Long id);

}
