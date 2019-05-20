package mate.academy.servlets.user;

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

@WebServlet(value = "/user/profile")
public class UserProfileServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserProfileServlet.class);
    private static final UserDao USER_DAO = new UserDaoHib();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newPass = request.getParameter("password").trim();
        String newMail = request.getParameter("mail").trim();
        User userFromSession = (User) request.getSession().getAttribute("user");
        if (newPass.length() > 5 && !newPass.equals(userFromSession.getPassword())) {
            userFromSession.setSalt(HashUtil.getRandomSalt());
            userFromSession.setPassword(newPass);
        }
        if (newMail.endsWith("@gmail.com")
                && newMail.length() > 15
                && !USER_DAO.getUserByMail(newMail).isPresent()) {
            userFromSession.setMail(newMail);
        }
        USER_DAO.editUser(userFromSession);
        String message = "You changed your data successfully!";
        logger.warn("User " + userFromSession.getId() + " changed his data");
        request.setAttribute("message", message);
        request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
    }
}
