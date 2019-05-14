package mate.academy.servlets;

import mate.academy.database.user.UserDao;
import mate.academy.database.user.UserDaoHib;
import mate.academy.model.User;
import mate.academy.util.HashUtil;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/login")
public class LogServlet extends HttpServlet {
    private static final UserDao USER_DAO = new UserDaoHib();
    private static final Logger logger = Logger.getLogger(LogServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("User started log in page");
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").trim();
        String password = req.getParameter("password").trim();
        logger.debug("User entered data " + login + " " + password);
        String message;
        if (USER_DAO.getUserByLogin(login).isPresent()) {
            User user = USER_DAO.getUserByLogin(login).get();
            String hashPass = HashUtil.getSha512SecurePassword(password, user.getSalt());
            if (user.getPassword().equals(hashPass)) {
                req.getSession().setAttribute("user", user);
                if (user.getRole().getId() == 1) {
                    logger.debug("Admin " + user.getLogin() + " logged in and started session");
                    message = "Welcome back admin " + login;
                    req.setAttribute("message", message);
                    req.getRequestDispatcher("/admin").forward(req, resp);
                } else {
                    logger.debug("User " + user.getLogin() + " logged in and started session");
                    message = "Welcome back " + login;
                    req.setAttribute("message", message);
                    req.getRequestDispatcher("/user/goods").forward(req, resp);
                }
            } else {
                logger.debug("User entered wrong login or password");
                message = "Wrong login or password";
                req.setAttribute("message", message);
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } else {
            logger.debug("User entered login which doesn't exist in database");
            message = "No user in base with name " + login;
            req.setAttribute("message", message);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
