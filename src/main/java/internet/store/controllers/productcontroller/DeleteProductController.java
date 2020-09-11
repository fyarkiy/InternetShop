package internet.store.controllers.productcontroller;

import internet.store.lib.Injector;
import internet.store.service.ProductService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProductController extends HttpServlet {
    public static final Injector injector = Injector.getInstance("internet.store");
    public final ProductService productService = (ProductService) injector
            .getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        productService.delete(Long.valueOf(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath() + "/product/manage");
    }
}
