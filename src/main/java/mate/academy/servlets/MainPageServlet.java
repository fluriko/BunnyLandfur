package mate.academy.servlets;

import mate.academy.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/main")
public class MainPageServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(MainPageServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else if (user.getRole().getId() == 1) {
            request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
        } else if (user.getRole().getId() == 2) {
            request.getRequestDispatcher("/user/index.jsp").forward(request, response);
        } else {
            logger.error("No action for " +  user.getInfo());
        }
    }
}
