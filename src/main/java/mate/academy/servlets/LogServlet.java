package mate.academy.servlets;

import mate.academy.database.UserDao;
import mate.academy.database.impl.UserDaoHibImpl;
import mate.academy.model.User;
import mate.academy.service.validator.UserValidationService;
import mate.academy.service.validator.ValidationService;
import mate.academy.util.HashUtil;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(value = "/login")
public class LogServlet extends HttpServlet {
    private static final ValidationService validationService = new UserValidationService();
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
        String password = req.getParameter("password").trim();
        String violations = validateData(login, password);
        if (violations.isEmpty()) {
            User user = userDao.getUserByLogin(login).get();
            req.getSession().setAttribute("user", user);
            logger.info(login + " logged in and started session");
            resp.sendRedirect("/main");
        } else {
            req.setAttribute("violations", violations);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    private String validateData(String login, String password) {
        String result = "";
        Optional<User> userOptional = userDao.getUserByLogin(login);
        if (!userOptional.isPresent()) {
            result = "login is not in base | ";
        } else {
            User userFromDb = userOptional.get();
            String passwordHash = HashUtil.getSha512SecurePassword(password, userFromDb.getSalt());
            result = validatePassword(userFromDb.getPassword(), passwordHash);
        }
        return result;
    }

    private String validatePassword(String passwordFromDb, String passwordFromForm) {
        if (passwordFromDb.equals(passwordFromForm)) {
            return "";
        } else {
            return "wrong login/password | ";
        }
    }
}
