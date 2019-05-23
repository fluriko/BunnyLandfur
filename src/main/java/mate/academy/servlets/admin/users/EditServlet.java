package mate.academy.servlets.admin.users;

import mate.academy.database.RoleDao;
import mate.academy.database.UserDao;
import mate.academy.database.impl.RoleDaoHibImpl;
import mate.academy.database.impl.UserDaoHibImpl;
import mate.academy.model.Role;
import mate.academy.model.User;
import mate.academy.service.validator.UserValidationService;
import mate.academy.service.validator.GenericValidationService;
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
    private static final UserValidationService validationService = new UserValidationService();
    private static final UserDao userDao = new UserDaoHibImpl();
    private static final RoleDao roleDao = new RoleDaoHibImpl();
    private static final Logger logger = Logger.getLogger(EditServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.parseLong(request.getParameter("id"));
        User userToEdit = userDao.get(userId).get();
        request.setAttribute("userToEdit", userToEdit);
        List<Role> roles = roleDao.getAll();
        roles.remove(userToEdit.getRole());
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("/admin/edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login").trim();
        String password = request.getParameter("password").trim();
        String mail = request.getParameter("mail").trim();
        Long roleId = Long.parseLong(request.getParameter("role"));
        Role role = roleDao.get(roleId).get();
        Long userId = Long.parseLong(request.getParameter("id"));
        User userToEdit = userDao.get(userId).get();
        setUserFields(userToEdit, login, password, mail, role);
        String violations = validationService.validate(userToEdit);
        User admin = (User) request.getSession().getAttribute("user");
        if (violations.isEmpty() && userDao.edit(userToEdit)) {
            logger.info(userToEdit.getInfo() + " edited by " + admin.getInfo());
            String message = userToEdit.getInfo() + " edited successfully";
            request.setAttribute("message", message);
            request.getRequestDispatcher("/admin/list").forward(request, response);
            return;
        } else if (violations.isEmpty()) {
            violations = "login/mail is not unique | ";
        }
        logger.debug(admin.getInfo() + " :editing user failed: " + violations);
        request.setAttribute("violations", violations);
        doGet(request, response);

    }

    private static void setUserFields(User user, String login, String password, String mail, Role role) {
        user.setLogin(login);
        if (password.isEmpty()) {
            user.setPasswordLength(6);
        } else {
            user.setSalt(HashUtil.getRandomSalt());
            user.setPassword(password);
            user.setPasswordLength(password.length());
        }
        user.setMail(mail);
        user.setRole(role);
    }
}
