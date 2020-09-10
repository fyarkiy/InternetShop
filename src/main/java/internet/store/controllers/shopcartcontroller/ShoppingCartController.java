package internet.store.controllers.shopcartcontroller;

import internet.store.lib.Injector;
import internet.store.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShoppingCartController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector injector = Injector
            .getInstance("internet.store");
    private final ShoppingCartService shoppingCartService = (ShoppingCartService) injector
            .getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("products", shoppingCartService
                .getByUserId(USER_ID).getProducts());
        req.getRequestDispatcher("/WEB-INF/views/shopcart/product.jsp")
                .forward(req, resp);
    }
}
