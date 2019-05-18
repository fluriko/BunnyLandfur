package mate.academy.servlets.admin.users;

import mate.academy.database.role.RoleDao;
import mate.academy.database.role.RoleDaoHib;
import mate.academy.database.user.UserDao;
import mate.academy.database.user.UserDaoHib;
import mate.academy.model.Role;
import mate.academy.model.User;
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
    private static final Logger logger = Logger.getLogger(AddUserServlet.class);
    private static final UserDao USER_DAO = new UserDaoHib();
    private static final RoleDao ROLE_DAO = new RoleDaoHib();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Role> roles = ROLE_DAO.getRoles();
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("/admin/addUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Role> roles = ROLE_DAO.getRoles();
        req.setAttribute("roles", roles);
        if (checkCorrect(req, resp) && checkUnique(req, resp)) {
            addUser(req, resp);
        }
    }

    private boolean checkCorrect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return checkCorrectLog(req, resp)
                && checkCorrectPass(req, resp)
                &&checkCorrectMail(req, resp);
    }

    private boolean checkUnique(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return checkUniqueLog(req, resp)
                && checkUniqueMail(req, resp);
    }

    private boolean checkCorrectLog(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").trim();
        if (login.length() < 4) {
            String message = "Login " + login + " is too short, enter 4 symbols at least\n";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/admin/addUser.jsp").forward(req, resp);
            return false;
        }
        return true;
    }

    private boolean checkCorrectPass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password").trim();
        if (password.length() < 6) {
            String message = "Password is too short, enter 6 symbols at least\n";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/admin/addUser.jsp").forward(req, resp);
            return false;
        }
        return true;
    }

    private boolean checkCorrectMail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mail = req.getParameter("mail").trim();
        if (!mail.endsWith("@gmail.com") || mail.length() < 16) {
            String message = "mail should be more than 16 chars long and should end with @gmail.com\n";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/admin/addUser.jsp").forward(req, resp);
            return false;
        }
        return true;
    }

    private boolean checkUniqueLog(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").trim();
        if (USER_DAO.getUserByLogin(login).isPresent()) {
            String message = "User with login " + login + " already exists";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/admin/addUser.jsp").forward(req, resp);
            return false;
        }
        return true;
    }

    private boolean checkUniqueMail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mail = req.getParameter("mail").trim();
        if (USER_DAO.getUserByMail(mail).isPresent()) {
            String message = "User with mail " + mail + " already exists";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/admin/addUser.jsp").forward(req, resp);
            return false;
        }
        return true;
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Role role = ROLE_DAO.getRole(2L).get();
        String login = req.getParameter("login").trim();
        String password = req.getParameter("password").trim();
        String mail = req.getParameter("mail").trim();
        String roleIdString = req.getParameter("role");
        if (roleIdString != null) {
            Long roleId = Long.parseLong(roleIdString);
            role = ROLE_DAO.getRole(roleId).get();
        }
        User user = new User(login, password, role, mail);
        USER_DAO.addUser(user);
        User userGet = USER_DAO.getUserByLogin(login).get();
        String message = userGet.getRole() + " " + userGet.getId() + " added successfully";
        logger.info(message);
        req.setAttribute("message", message);
        req.getRequestDispatcher("/admin/list").forward(req, resp);
    }
}
