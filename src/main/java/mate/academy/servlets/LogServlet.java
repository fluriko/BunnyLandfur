package mate.academy.servlets;

import mate.academy.database.UserDao;
import mate.academy.database.impl.UserDaoHibImpl;
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
    private static final UserDao userDao = new UserDaoHibImpl();
    private static final Logger logger = Logger.getLogger(LogServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Guest started log in page");
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").trim();
        String message;
        if (userDao.getUserByLogin(login).isPresent()) {
            checkPassword(req, resp);
        } else {
            message = "No user in base with name " + login;
            req.setAttribute("message", message);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    private void checkPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").trim();
        User user = userDao.getUserByLogin(login).get();
        String password = req.getParameter("password").trim();
        String hashPass = HashUtil.getSha512SecurePassword(password, user.getSalt());
        if (user.getPassword().equals(hashPass)) {
            req.getSession().setAttribute("user", user);
            logger.debug(user.getInfo() + " logged in and started session");
            resp.sendRedirect("/main");
        } else {
            logger.info("Guest entered wrong login or password");
            String message = "Wrong login or password";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
