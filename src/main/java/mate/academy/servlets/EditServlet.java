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
import java.util.List;

@WebServlet(value = "/adminedit")
public class EditServlet extends HttpServlet {
    private static final UserDao USER_DAO = new UserDao();
    private static final Logger logger = Logger.getLogger(EditServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Admin started edit page");
        req.setAttribute("name", req.getParameter("name"));
        req.getRequestDispatcher("edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String newPass = req.getParameter("password").trim();
        logger.debug("Admin tried to set " + name + " new password " + newPass);
        if (newPass.length() == 0) {
            logger.debug("Password field is empty, redirecting to list");
            List<User> users = USER_DAO.getUsers();
            req.setAttribute("users", users);
            req.getRequestDispatcher("/admin").forward(req, resp);
        } else if (newPass.length() < 6) {
            logger.debug("Password field is too short, restarted edit page");
            req.setAttribute("error", "Too short password, try again");
            req.getRequestDispatcher("edit.jsp").forward(req, resp);
        } else {
            logger.info("Admin changed password for " + name + ": " + newPass);
            USER_DAO.editUser(name, newPass);
            List<User> users = USER_DAO.getUsers();
            req.setAttribute("users", users);
            req.setAttribute("message", "User " + name + " was edited successfully!");
            req.getRequestDispatcher("/admin").forward(req, resp);
        }
    }
}
