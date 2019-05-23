package mate.academy.servlets;

import mate.academy.database.UserDao;
import mate.academy.database.impl.UserDaoHibImpl;
import mate.academy.model.User;
import mate.academy.service.validator.UserValidationService;
import mate.academy.service.validator.GenericValidationService;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/registration")
public class RegServlet extends HttpServlet {
    private static final GenericValidationService validationService = new UserValidationService();
    private static final UserDao userDao = new UserDaoHibImpl();
    private static final Logger logger = Logger.getLogger(RegServlet.class);
    private static final String INSTRUCTION = "login should be 4-16 chars, " +
            "password should be 6-16 chars, " +
            "mail should be real mail address, " +
            "all data should be uniq ";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Guest started registration");
        request.setAttribute("instruction", INSTRUCTION);
        request.getRequestDispatcher("/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login").trim();
        String password = request.getParameter("password").trim();
        String mail = request.getParameter("mail").trim();
        User user = new User(login, password, mail);
        String violations = validationService.validate(user);
        if (violations.isEmpty() && userDao.add(user)) {
            User userJustRegistered = userDao.getUserByLogin(user.getLogin()).get();
            logger.info(userJustRegistered.getInfo() + " registered successfully");
            response.sendRedirect("/login");
        } else {//TODO NOT UNIQ DATA MESSAGE
            logger.debug("Registration failed: " + violations);
            request.setAttribute("violations", violations);
            doGet(request, response);
        }
    }
}
