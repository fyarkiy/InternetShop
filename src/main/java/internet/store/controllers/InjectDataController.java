package internet.store.controllers;

import internet.store.lib.Injector;
import internet.store.model.Role;
import internet.store.model.User;
import internet.store.service.ProductService;
import internet.store.service.ShoppingCartService;
import internet.store.service.UserService;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.store");
    private final UserService userService = (UserService) injector
            .getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService = (ShoppingCartService) injector
            .getInstance(ShoppingCartService.class);
    private final ProductService productService = (ProductService) injector
            .getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User newAdmin = new User("newAdmin", "admin", "1");
        newAdmin.setRoles(Set.of(Role.of("ADMIN")));
        userService.create(newAdmin);
    }
}
