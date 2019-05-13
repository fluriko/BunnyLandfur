package mate.academy.servlets;

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

@WebServlet(value = "/admin/addUser")
public class AddUserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddUserServlet.class);
    private static final UserDao USER_DAO = new UserDaoHib();
    private static final RoleDao ROLE_DAO = new RoleDaoHib();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login").trim();
        String password = request.getParameter("password").trim();
        String mail = request.getParameter("mail").trim();
        Role role = ROLE_DAO.getRole(2).get();
        String checkLogCorrect = checkCorrect(login.length() < 4, "login");
        String checkPassCorrect = checkCorrect(password.length() < 6, "password");
        String checkMailCorrect = checkCorrect(!mail.endsWith("@gmail.com") || mail.length() < 16, "mail");
        if (checkLogCorrect.length() > 0 || checkPassCorrect.length() > 0 || checkMailCorrect.length() > 0) {
            String message = checkLogCorrect + checkPassCorrect + checkMailCorrect;
            request.setAttribute("message", message);
            List<Role> roles = ROLE_DAO.getRoles();
            request.setAttribute("roles", roles);
            request.getRequestDispatcher("/admin/addUser.jsp").forward(request, response);
        } else {
            String checkLogin = checkUniq(USER_DAO.getUserByLogin(login).isPresent(), "login " + login);
            String checkMail = checkUniq(USER_DAO.getUserByMail(mail).isPresent(), "mail " + mail);
            if (checkLogin.length() > 0 || checkMail.length() > 0) {
                String message = checkLogin + checkMail;
                request.setAttribute("message", message);
                List<Role> roles = ROLE_DAO.getRoles();
                request.setAttribute("roles", roles);
                request.getRequestDispatcher("/admin/addUser.jsp").forward(request, response);
            } else {
                String roleIdString = request.getParameter("role");
                if (roleIdString != null) {
                    int roleId = Integer.parseInt(roleIdString);
                    role = ROLE_DAO.getRole(roleId).get();
                }
                String salt = HashUtil.getRandomSalt();
                String hashPass = HashUtil.getSha512SecurePassword(password, salt);
                User user = new User(login, hashPass, role, mail, salt);
                USER_DAO.addUser(user);
                User userGet = USER_DAO.getUserByLogin(login).get();
                String message = userGet.getRole() + " " + userGet.getId() + " added successfully";
                logger.debug(message);
                request.setAttribute("message", message);
                request.getRequestDispatcher("/admin/list").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Role> roles = ROLE_DAO.getRoles();
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("/admin/addUser.jsp").forward(request, response);
    }

    private String checkCorrect(boolean condition, String data) {
        String message = "";
        if (condition) {
            message = data + " is not correct!\n";
        }
        return message;
    }

    private String checkUniq(boolean condition, String data) {
        String message = "";
        if (condition) {
            message = "User with " + data + " already registered!\n";
        }
        return message;
    }
}
