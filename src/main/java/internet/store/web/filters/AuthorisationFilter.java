package internet.store.web.filters;

import internet.store.lib.Injector;
import internet.store.model.Role;
import internet.store.model.User;
import internet.store.service.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorisationFilter implements Filter {
    private static final String USER_ID = "user_id";
    private static final Injector injector = Injector.getInstance("internet.store");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final Map<String, Set<Role.RoleName>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/order/all", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/order/delete", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/product/create", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/product/delete", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/product/manage", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/user/all", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/user/delete", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/order/create", Set.of(Role.RoleName.USER));
        protectedUrls.put("/shopping-cart/product", Set.of(Role.RoleName.USER));
        protectedUrls.put("/shopping-cart/product/add", Set.of(Role.RoleName.USER));
        protectedUrls.put("/shopping-cart/product/remove", Set.of(Role.RoleName.USER));
        protectedUrls.put("/user/order", Set.of(Role.RoleName.USER));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestedUrl = req.getServletPath();

        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        if (!protectedUrls.containsKey(requestedUrl)
                || isAuthourised(userService.get(userId), protectedUrls.get(requestedUrl))) {
            chain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/user/access-denied.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isAuthourised(User user, Set<Role.RoleName> authorisedRoles) {
        for (Role.RoleName authorisedRole : authorisedRoles) {
            for (Role userRole : user.getRoles()) {
                if (userRole.getRoleName().equals(authorisedRole)) {
                    return true;
                }
            }
        }
        return false;
    }
}
