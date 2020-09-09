package internet.store.dao;

import internet.store.model.Order;
import java.util.List;

public interface OrderDao extends GenericDao<Order, Long> {
    List<Order> getUserOrders(Long userId);
}
