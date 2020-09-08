package internet.store.service;

import internet.store.model.Order;
import internet.store.model.ShoppingCart;
import java.util.List;

public interface OrderService extends GenericService<Order, Long, Long> {

    Order completeOrder(ShoppingCart shoppingCart);

    List<Order> getUserOrders(Long userId);
}
