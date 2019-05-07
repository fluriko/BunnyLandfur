package mate.academy.servlets;

import mate.academy.database.UserDao;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/admin/showPass")
public class ShowPassServlet extends HttpServlet {
    private static final UserDao USER_DAO = new UserDao();
    private static final Logger logger = Logger.getLogger(ShowPassServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        logger.info("Admin watches password hash for user: " + userId);
        String hashPass = USER_DAO.getUserById(userId).get().getPassword();
        request.setAttribute("hashPass", hashPass);
        request.getRequestDispatcher("showPass.jsp").forward(request, response);
    }
}
