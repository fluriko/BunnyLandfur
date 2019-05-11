package mate.academy.servlets;

import mate.academy.database.user.UserDao;
import mate.academy.database.user.UserDaoHib;
import mate.academy.database.user.UserDaoJdbc;
import mate.academy.model.Roles;
import mate.academy.model.User;
import mate.academy.util.HashUtil;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/admin/edit")
public class EditServlet extends HttpServlet {
    private static final UserDao USER_DAO = new UserDaoHib();
    private static final Logger logger = Logger.getLogger(EditServlet.class);
    private static int userId;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Admin started edit page");
        userId = Integer.parseInt(req.getParameter("id"));
        req.getRequestDispatcher("/admin/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User userOld = USER_DAO.getUserById(userId).get();
        String newLog = req.getParameter("login").trim();
        String newPass = req.getParameter("password").trim();
        int roleId = userOld.getRoleId();
        String newMail = req.getParameter("mail").trim();
        String newSalt = HashUtil.getRandomSalt();
        String message = "For user " + userId + ": ";
        logger.debug("Admin tried to set " + userId + " new data ");
        if (newLog.length() < 4 || USER_DAO.containsLogin(newLog)) {
            newLog = userOld.getLogin();
            message += " login didn't changed, ";
        } else {
            message += " login changed, ";
        }
        if (newPass.length() < 6 || newPass.equals(userOld.getPassword())) {
            newPass = userOld.getPassword();
            newSalt = userOld.getSalt();
            message += " password didn't changed, ";
        } else {
            message += " password changed, ";
        }
        if (!newMail.endsWith("@gmail.com") || newMail.length() < 16 || USER_DAO.containsMail(newMail)) {
            newMail = userOld.getMail();
            message += " mail didn't changed. ";
        } else {
            message += " mail changed. ";
        }
        String setAdmin = req.getParameter("setAdmin");
        if (setAdmin != null && setAdmin.equals("admin")) {
            roleId = Roles.ADMIN.getId();
            message += " User " + userId + " got admin rights. ";
        }
        String setUser = req.getParameter("setUser");
        if (setUser != null && setUser.equals("user")) {
            roleId = Roles.USER.getId();
            message += " User " + userId + " got user rights. ";
        }
        logger.info("Admin changed user data for " + userId);
        User userNew = new User(userId, newLog, newPass, roleId, newMail, newSalt);
        USER_DAO.editUser(userNew);
        req.setAttribute("message", message);
        userId = 0;
        req.getRequestDispatcher("/admin/list").forward(req, resp);
    }
}
