package internet.store.controllers;

import internet.store.lib.Injector;
import internet.store.model.Product;
import internet.store.model.ShoppingCart;
import internet.store.model.User;
import internet.store.service.ProductService;
import internet.store.service.ShoppingCartService;
import internet.store.service.UserService;
import java.io.IOException;
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
        User daniel = new User("Daniel", "dan", "123");
        userService.create(daniel);
        shoppingCartService.create(new ShoppingCart(daniel.getUserId()));
        User sergey = new User("Sergey", "serg", "4321");
        userService.create(sergey);
        shoppingCartService.create(new ShoppingCart(sergey.getUserId()));
        User mandy = new User("Mandy", "Mandy", "1234321");
        userService.create(mandy);
        shoppingCartService.create(new ShoppingCart(mandy.getUserId()));
        User joanne = new User("Joanne", "Joe", "1234321");
        userService.create(joanne);
        shoppingCartService.create(new ShoppingCart(joanne.getUserId()));
        productService.create(new Product("Knife", 10));
        productService.create(new Product("Pot", 100));
        productService.create(new Product("Napkins", 2));
        req.getRequestDispatcher("/WEB-INF/views/user/inject.jsp").forward(req, resp);
    }
}
