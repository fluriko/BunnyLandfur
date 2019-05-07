package mate.academy.servlets;

import mate.academy.database.UserDao;
import mate.academy.model.Roles;
import mate.academy.model.User;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/admin/edit")
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
        String message = "For user " + userId;
        logger.debug("Admin tried to set " + userId + " new data ");
        if (newPass.length() < 6) {
            newPass = user.getPassword();
            message += ": password didn't changed, ";
        } else {
            message += ": password changed, ";
        }
        if (newLog.length() < 4 || USER_DAO.contains(newLog)) {
            newLog = user.getName();
            message += " login didn't changed, ";
        } else {
            message += " login changed, ";
        }
        if (!newMail.endsWith("@gmail.com") || newMail.length() < 16 || USER_DAO.containsMail(newMail)) {
            newMail = user.getMail();
            message += " mail didn't changed. ";
        } else {
            message += " mail changed. ";
        }
        logger.info("Admin changed user data for " + userId);
        USER_DAO.editUser(userId, newLog, newPass, newMail);
        String setAdmin = req.getParameter("setAdmin");
        if (setAdmin != null && setAdmin.equals("admin")) {
            USER_DAO.setRole(userId, Roles.ADMIN.getId());
            message += " User " + userId + " got admin rights. ";
        }
        String setUser = req.getParameter("setUser");
        if (setUser != null && setUser.equals("user")) {
            USER_DAO.setRole(userId, Roles.USER.getId());
            message += " User " + userId + " got user rights. ";
        }
        req.setAttribute("message", message);
        userId = 0;
        req.getRequestDispatcher("/admin/list").forward(req, resp);
    }
}
