package mate.academy.servlets.admin.users;

import mate.academy.database.UserDao;
import mate.academy.database.impl.UserDaoHibImpl;
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
    private static final UserDao userDao = new UserDaoHibImpl();
    private static final Logger logger = Logger.getLogger(DeleteServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        User user = userDao.get(id).get();
        User admin = (User) req.getSession().getAttribute("user");
        String message;
        if (user.getRole().getId().equals(1L)) {
            logger.debug(admin.getInfo() + " can't delete " + user.getInfo());
            message = "You can't delete " + user.getRole() + " " + id;
        } else {
            userDao.remove(user);
            logger.warn(admin.getInfo() +  " deleted " + user);
            message = user.getRole() + " " + id + " was deleted successfully!";
        }
        req.setAttribute("message", message);
        req.getRequestDispatcher("/admin/list").forward(req, resp);
    }
}
