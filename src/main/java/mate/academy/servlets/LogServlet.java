package mate.academy.servlets;

import mate.academy.database.UserDao;
import mate.academy.model.User;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/login")
public class LogServlet extends HttpServlet {
    private static final UserDao USER_DAO = new UserDao();
    private static final Logger logger = Logger.getLogger(LogServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("User started log in page");
        req.setAttribute("message", "");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name").trim();
        String password = req.getParameter("password").trim();
        logger.debug("User entered data " + name + " " + password);
        String message;
        if (USER_DAO.contains(name)) {
            if (USER_DAO.getUser(name).get().getPassword().equals(password)) {
                User user = USER_DAO.getUser(name).get();
                req.getSession().setAttribute("user", user);
                if (user.getRole().getId() == 1) {
                    logger.debug("Admin " + user.getName() + " logged in and started session");
                    message = "Welcome back admin " + name;
                    req.setAttribute("message", message);
                    req.getRequestDispatcher("/admin").forward(req, resp);
                } else {
                    logger.debug("User " + user.getName() + " logged in and started session");
                    message = "Welcome back " + name;
                    req.setAttribute("message", message);
                    req.getRequestDispatcher("index.jsp").forward(req, resp);
                }
            } else {
                logger.debug("User entered wrong login or password");
                message = "Wrong login or password";
                req.setAttribute("message", message);
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } else {
            logger.debug("User entered login which doesn't exist in database");
            message = "No user in base with name " + name;
            req.setAttribute("message", message);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
