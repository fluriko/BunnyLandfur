package mate.academy.servlets;

import mate.academy.database.Database;
import mate.academy.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/list")
public class ListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("message", "");
        List<User> users = Database.getUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher("list.jsp").forward(req, resp);
    }
}
