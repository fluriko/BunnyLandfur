package mate.academy.servlets.admin;

import mate.academy.model.User;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/admin")
public class AdminServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AdminServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User admin = (User) request.getSession().getAttribute("user");
        logger.info(admin.getInfo() + " is on main admin page");
        request.getRequestDispatcher("/admin/admin.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
