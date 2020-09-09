package internet.store.controllers.usercontroller;

import internet.store.lib.Injector;
import internet.store.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.store");
    private UserService userService = (UserService) injector
            .getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("users", userService.getAll());
        req.getRequestDispatcher("/WEB-INF/views/user/all.jsp").forward(req, resp);
    }
}
