package mate.academy.servlets;

import mate.academy.database.Database;
import mate.academy.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(value = "/delete")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        User user = new User(name);
        Database.removeUser(user);
        List<User> users = Database.getUsers();
        req.setAttribute("users", users);
        req.setAttribute("message", "User " + name + " was deleted successfully!");
        req.getRequestDispatcher("list.jsp").forward(req, resp);
    }
}
