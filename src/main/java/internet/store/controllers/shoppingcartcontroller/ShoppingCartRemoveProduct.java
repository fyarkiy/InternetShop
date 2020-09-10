package internet.store.controllers.shopcartcontroller;

import internet.store.lib.Injector;
import internet.store.service.ProductService;
import internet.store.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShoppingCartRemoveProduct extends HttpServlet {
    private final static Long USER_ID = 1L;
    private final static Injector injector = Injector.getInstance("internet.store");
    private final ShoppingCartService shoppingCartService = (ShoppingCartService) injector
            .getInstance(ShoppingCartService.class);
    private final ProductService productService = (ProductService) injector
            .getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long product = Long.valueOf(req.getParameter("id"));
        shoppingCartService.deleteProduct(shoppingCartService.getByUserId(USER_ID),
                productService.get(product));
        resp.sendRedirect(req.getContextPath() + "/shoppingcart/product");
    }
}
