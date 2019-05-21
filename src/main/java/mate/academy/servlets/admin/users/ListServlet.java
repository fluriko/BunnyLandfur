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
import java.util.List;

@WebServlet(value = "/admin/list")
public class ListServlet extends HttpServlet {
    private static final UserDao userDao = new UserDaoHibImpl();
    private static final Logger logger = Logger.getLogger(ListServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User admin = (User) req.getSession().getAttribute("user");
        logger.info(admin.getInfo() + " is on users list page");
        List<User> users = userDao.getAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/admin/list.jsp").forward(req, resp);
    }
}
