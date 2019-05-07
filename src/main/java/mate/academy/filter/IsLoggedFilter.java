package mate.academy.filter;

import mate.academy.model.User;
import org.apache.log4j.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/user/*")
public class IsLoggedFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AdminFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("logged in filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = (User) request.getSession().getAttribute("user");
        logger.debug("doing logged in filter");
        if (user != null) {
            logger.debug("logged in filter accepted user");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            logger.debug("logged in filter denied user");
            request.setAttribute("message", "You are not logged in, access denied!");
            request.getRequestDispatcher("/index.jsp").forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        logger.debug("logged in filter destroy");
    }
}
