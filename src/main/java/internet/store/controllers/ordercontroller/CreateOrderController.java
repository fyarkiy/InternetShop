package internet.store.controllers.ordercontroller;

import internet.store.lib.Injector;
import internet.store.service.OrderService;
import internet.store.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateOrderController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector injector = Injector
            .getInstance("internet.store");
    private final OrderService orderService = (OrderService) injector
            .getInstance(OrderService.class);
    private final ShoppingCartService shoppingCartService = (ShoppingCartService) injector
            .getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        orderService.completeOrder(shoppingCartService.getByUserId(USER_ID));
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
