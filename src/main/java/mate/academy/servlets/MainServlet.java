package mate.academy.servlets;

import mate.academy.database.UserDao;
import mate.academy.model.Roles;
import mate.academy.model.User;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/")
public class MainServlet extends HttpServlet {
    private static final UserDao USER_DAO = new UserDao();
    private static final Logger logger = Logger.getLogger(MainServlet.class);
    private static final User ADMIN_FLURIKO = new User("fluriko", "123123", Roles.ADMIN);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        USER_DAO.addUser(ADMIN_FLURIKO);
        logger.debug("User is on main page");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
