package mate.academy.servlets;

import mate.academy.database.user.UserDao;
import mate.academy.database.user.UserDaoHib;
import mate.academy.model.User;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/admin/delete")
public class DeleteServlet extends HttpServlet {
    private static final UserDao USER_DAO = new UserDaoHib();
    private static final Logger logger = Logger.getLogger(DeleteServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        User user = USER_DAO.getUserById(id).get();
        logger.debug("Admin tried to delete " + id);
        String message;
        if (USER_DAO.removeUser(user) == 0) {
            logger.debug("Admin can't delete " + id);
            message = "You can't delete user " + id;
        } else {
            logger.info("Admin deleted " + id);
            message = "User " + id + " was deleted successfully!";
        }
        req.setAttribute("message", message);
        req.getRequestDispatcher("/admin/delete.jsp").forward(req, resp);
    }
}
