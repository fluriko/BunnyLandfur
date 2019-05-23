package mate.academy.servlets.admin.users;

import mate.academy.database.RoleDao;
import mate.academy.database.UserDao;
import mate.academy.database.impl.RoleDaoHibImpl;
import mate.academy.database.impl.UserDaoHibImpl;
import mate.academy.model.Role;
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
import java.util.List;

@WebServlet(value = "/admin/addUser")
public class AddUserServlet extends HttpServlet {
    private static final ValidationService validationService = new UserValidationService();
    private static final Logger logger = Logger.getLogger(AddUserServlet.class);
    private static final UserDao userDao = new UserDaoHibImpl();
    private static final RoleDao roleDao = new RoleDaoHibImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Role> roles = roleDao.getAll();
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("/admin/addUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").trim();
        String password = req.getParameter("password").trim();
        String mail = req.getParameter("mail").trim();
        Long roleId = Long.parseLong(req.getParameter("role"));
        Role role = roleDao.get(roleId).get();
        User user = new User(login, password, role, mail);
        String violations = validationService.validate(user);
        User admin = (User) req.getSession().getAttribute("user");
        if (violations.isEmpty()) {
            userDao.add(user);
            User userJustRegistered = userDao.getUserByLogin(user.getLogin()).get();
            logger.info(userJustRegistered.getInfo() + " registered by " + admin.getInfo());
            String message = userJustRegistered.getInfo() + " added successfully";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/admin/list").forward(req, resp);
        } else {
            logger.debug(admin.getInfo() + " :adding user failed: " + violations);
            req.setAttribute("message", violations);
            doGet(req, resp);
        }
    }
}
