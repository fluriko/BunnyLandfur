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

@WebServlet(value = "/admindelete")
public class DeleteServlet extends HttpServlet {
    private static final UserDao USER_DAO = new UserDao();
    private static final Logger logger = Logger.getLogger(DeleteServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        logger.debug("Admin tried to delete " + name);
        String message;
        if (USER_DAO.removeUser(name) == 0) {
            logger.debug("Admin can't delete " + name);
            message = "You can't delete user " + name;
        } else {
            logger.info("Admin deleted " + name);
            message = "User " + name + " was deleted successfully!";
        }
        req.setAttribute("message", message);
        List<User> users = USER_DAO.getUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/admin").forward(req, resp);
    }
}
