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

@WebServlet(value = "/edit")
public class EditServlet extends HttpServlet {
    private static final UserDao USER_DAO = new UserDao();
    private static final Logger logger = Logger.getLogger(EditServlet.class);
    private static int userId;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Admin started edit page");
        userId = Integer.parseInt(req.getParameter("id"));
        req.getRequestDispatcher("edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = USER_DAO.getUserById(userId).get();
        String newLog = req.getParameter("login").trim();
        String newPass = req.getParameter("password").trim();
        String newMail = req.getParameter("mail").trim();
        logger.debug("Admin tried to set " + userId + " new data ");
        if (newPass.length() < 6) {
            newPass = user.getPassword();
        }
        if (newLog.length() < 4 || USER_DAO.contains(newLog)) {
            newLog = user.getName();
        }
        if (!newMail.endsWith("@gmail.com") || newMail.length() < 16 || USER_DAO.containsMail(newMail)) {
            newMail = user.getMail();
        }
        logger.info("Admin changed user data for " + userId);
        USER_DAO.editUser(userId, newLog, newPass, newMail);
        req.setAttribute("message", "User with id " + userId + " was edited successfully!");
        userId = 0;
        req.getRequestDispatcher("/admin").forward(req, resp);
    }
}
