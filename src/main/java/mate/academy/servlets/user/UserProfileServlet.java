package mate.academy.servlets.user;

import mate.academy.database.UserDao;
import mate.academy.database.impl.UserDaoHibImpl;
import mate.academy.model.User;
import mate.academy.service.validator.UserValidationService;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/user/profile")
public class UserProfileServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserProfileServlet.class);
    private static final UserDao userDao = new UserDaoHibImpl();
    private static final UserValidationService validationService = new UserValidationService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newPass = request.getParameter("password").trim();
        String newMail = request.getParameter("mail").trim();
        String passwordOld = request.getParameter("passwordOld").trim();
        User user = (User) request.getSession().getAttribute("user");
        String violations = validationService.validateDataForLogIn(user.getLogin(), passwordOld);
        if (!violations.isEmpty()) {
            request.setAttribute("violations", "wrong old password | ");
            doGet(request, response);
            return;
        }
        user.setFields(newPass, newMail);
        violations = validationService.validate(user);
        if (violations.isEmpty() && userDao.edit(user)) {
            logger.info(user.getInfo() + " changed his data ");
            String message = "You changed your data successfully!";
            request.setAttribute("message", message);
            request.getRequestDispatcher("/main").forward(request, response);
            return;
        } else if (violations.isEmpty()) {
            violations = "mail is not unique | ";
        }
        logger.debug(user.getInfo() + ": changing profile failed " + violations);
        request.setAttribute("violations", violations);
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
    }
}
