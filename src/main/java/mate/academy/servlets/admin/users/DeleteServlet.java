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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        User user = userDao.get(id).get();
        User admin = (User) request.getSession().getAttribute("user");
        String message;
        if (user.getRole().getId().equals(1L)) {
            logger.debug(admin.getInfo() + " can't delete " + user.getInfo());
            message = "You can't delete " + user.getRole() + " " + id;
        } else {
            userDao.remove(user);
            logger.warn(admin.getInfo() +  " deleted " + user);
            message = user.getRole() + " " + id + " was deleted successfully!";
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("/admin/list").forward(request, response);
    }
}
