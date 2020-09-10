package internet.store.controllers.productcontroller;

import internet.store.lib.Injector;
import internet.store.model.Product;
import internet.store.service.ProductService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductSetUp extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.store");
    private final ProductService productService = (ProductService) injector
            .getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/product/setUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String productName = req.getParameter("productName");
        Long price = Long.valueOf(req.getParameter("price"));
        productService.create(new Product(productName, price));
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
