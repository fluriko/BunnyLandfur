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

@WebServlet(value = "/edit")
public class EditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("error", "");
        req.getRequestDispatcher("edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String newPass = req.getParameter("password");
        if (newPass.length() < 6) {
            req.setAttribute("error", "Too short password, try again");
            req.getRequestDispatcher("edit.jsp").forward(req, resp);
        } else {
            Database.editUser(new User(name), newPass);
            List<User> users = Database.getUsers();
            req.setAttribute("users", users);
            req.setAttribute("message", "User " + name + " was edited successfully!");
            req.getRequestDispatcher("list.jsp").forward(req, resp);
        }
    }
}
