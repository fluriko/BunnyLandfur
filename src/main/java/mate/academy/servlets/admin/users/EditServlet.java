package mate.academy.servlets.admin.users;

import mate.academy.database.RoleDao;
import mate.academy.database.UserDao;
import mate.academy.database.impl.RoleDaoHibImpl;
import mate.academy.database.impl.UserDaoHibImpl;
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
    private static final UserDao userDao = new UserDaoHibImpl();
    private static final RoleDao roleDaoHib = new RoleDaoHibImpl();
    private static final Logger logger = Logger.getLogger(EditServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = Long.parseLong(req.getParameter("id"));
        User user = userDao.get(userId).get();
        req.setAttribute("user", user);
        List<Role> roles = roleDaoHib.getAll();
        req.setAttribute("roles", roles);
        req.getRequestDispatcher("/admin/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = Long.parseLong(req.getParameter("id"));
        User userToEdit = userDao.get(userId).get();
        String newLog = req.getParameter("login").trim();
        String newPass = req.getParameter("password").trim();
        String newMail = req.getParameter("mail").trim();
        if (newLog.length() > 3 && !userDao.getUserByLogin(newLog).isPresent()) {
            userToEdit.setLogin(newLog);
        }
        if (newPass.length() > 5 && !newPass.equals(userToEdit.getPassword())) {
            userToEdit.setSalt(HashUtil.getRandomSalt());
            userToEdit.setPassword(newPass);
        }
        if (newMail.endsWith("@gmail.com")
                && newMail.length() > 15
                && !userDao.getUserByMail(newMail).isPresent()) {
            userToEdit.setMail(newMail);
        }
        String message = "You have changed data for " + userToEdit.getRole() + " " + userToEdit.getId();
        String roleIdString = req.getParameter("role");
        if (roleIdString != null) {
            Long roleId = Long.parseLong(roleIdString);
            userToEdit.setRole(roleDaoHib.get(roleId).get());
        }
        userDao.edit(userToEdit);
        User admin = (User) req.getSession().getAttribute("user");
        logger.warn(admin.getInfo() + "changed data for" + userToEdit.getInfo());
        req.setAttribute("message", message);
        req.getRequestDispatcher("/admin/list").forward(req, resp);
    }
}
