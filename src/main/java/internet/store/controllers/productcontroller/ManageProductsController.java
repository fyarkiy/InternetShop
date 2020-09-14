package internet.store.controllers.productcontroller;

import internet.store.lib.Injector;
import internet.store.service.ProductService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageProductsController extends HttpServlet {
    public static final Injector injector = Injector
            .getInstance("internet.store");
    public final ProductService productService = (ProductService) injector
            .getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("products", productService.getAll());
        req.getRequestDispatcher("/WEB-INF/views/product/manage.jsp").forward(req, resp);
    }
}
