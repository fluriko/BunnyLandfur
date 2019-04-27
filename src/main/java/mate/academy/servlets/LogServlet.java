package mate.academy.servlets;

import mate.academy.database.Database;
import mate.academy.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/login")
public class LogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("message", "");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        User user = new User(name, password);
        String message;
        if (Database.contains(user)) {
            if (Database.getUser(user).getPassword().equals(user.getPassword())) {
                message = "Welcome back " + user.getName();
                req.setAttribute("message", message);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            } else {
                message = "Wrong login or password";
                req.setAttribute("message", message);
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } else {
            message = "No user in base with name " + user.getName();
            req.setAttribute("message", message);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
