package internet.store.controllers;

import internet.store.lib.Injector;
import internet.store.model.User;
import internet.store.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InjectDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.store");
    UserService userService = (UserService) injector.getInstance(UserService.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.create(new User("Daniel", "dan", "123"));
        userService.create(new User("Sergey", "serg", "4321"));
        userService.create(new User("Mandy", "Mandy", "1234321"));
        userService.create(new User("Joanne", "Joe", "1234321"));



        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req,resp);
    }
}
