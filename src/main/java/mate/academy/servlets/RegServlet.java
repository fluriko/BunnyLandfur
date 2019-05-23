package mate.academy.servlets;

import mate.academy.database.UserDao;
import mate.academy.database.impl.UserDaoHibImpl;
import mate.academy.model.User;
import mate.academy.service.validator.UserValidationService;
import mate.academy.service.validator.ValidationService;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/registration")
public class RegServlet extends HttpServlet {
    private static final ValidationService validationService = new UserValidationService();
    private static final UserDao userDao = new UserDaoHibImpl();
    private static final Logger logger = Logger.getLogger(RegServlet.class);
    private static final String INSTRUCTION = "login should be 4-16 chars,\n" +
            "password should be 6-16 chars,\n" +
            "mail should be real mail address\n";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Guest started registration");
        req.setAttribute("instruction", INSTRUCTION);
        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").trim();
        String password = req.getParameter("password").trim();
        String mail = req.getParameter("mail").trim();
        User user = new User(login, password, mail);
        String violations = validationService.validate(user);
        if (violations.isEmpty()) {
            userDao.add(user);
            User userJustRegistered = userDao.getUserByLogin(user.getLogin()).get();
            logger.info(userJustRegistered.getInfo() + " registered successfully");
            resp.sendRedirect("/login");
        } else {
            logger.debug("Registration failed: " + violations);
            req.setAttribute("violations", violations);
            doGet(req, resp);
        }
    }
}
