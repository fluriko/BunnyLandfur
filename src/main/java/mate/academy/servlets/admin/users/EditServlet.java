package mate.academy.servlets.admin.users;

import mate.academy.database.role.RoleDao;
import mate.academy.database.role.RoleDaoHib;
import mate.academy.database.user.UserDao;
import mate.academy.database.user.UserDaoHib;
import mate.academy.model.Role;
import mate.academy.model.User;
import mate.academy.util.HashUtil;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/admin/edit")
public class EditServlet extends HttpServlet {
    private static final UserDao USER_DAO = new UserDaoHib();
    private static final RoleDao ROLE_DAO = new RoleDaoHib();
    private static final Logger logger = Logger.getLogger(EditServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = Long.parseLong(req.getParameter("id"));
        User user = USER_DAO.getUserById(userId).get();
        req.setAttribute("user", user);
        List<Role> roles = ROLE_DAO.getRoles();
        req.setAttribute("roles", roles);
        req.getRequestDispatcher("/admin/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = Long.parseLong(req.getParameter("id"));
        User userToEdit = USER_DAO.getUserById(userId).get();
        String newLog = req.getParameter("login").trim();
        String newPass = req.getParameter("password").trim();
        String newMail = req.getParameter("mail").trim();
        if (newLog.length() > 3 && !USER_DAO.getUserByLogin(newLog).isPresent()) {
            userToEdit.setLogin(newLog);
        }
        if (newPass.length() > 5 && !newPass.equals(userToEdit.getPassword())) {
            userToEdit.setSalt(HashUtil.getRandomSalt());
            userToEdit.setPassword(newPass);
        }
        if (newMail.endsWith("@gmail.com")
                && newMail.length() > 15
                && !USER_DAO.getUserByMail(newMail).isPresent()) {
            userToEdit.setMail(newMail);
        }
        String roleIdString = req.getParameter("role");
        if (roleIdString != null) {
            Long roleId = Long.parseLong(roleIdString);
            userToEdit.setRole(ROLE_DAO.getRole(roleId).get());
        }
        USER_DAO.editUser(userToEdit);
        String message = "Admin changed user data for " + userId;
        logger.warn(message);
        req.setAttribute("message", message);
        req.getRequestDispatcher("/admin/list").forward(req, resp);
    }
}
