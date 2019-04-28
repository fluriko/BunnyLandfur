package mate.academy.servlets;

import mate.academy.database.Database;
import mate.academy.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/registration")
public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("message", "");
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name").trim();
        String password = req.getParameter("password").trim();
        User user = new User(name, password);
        String message;
        if (user.getName().length() < 4) {
            message = "Name " + user.getName() + " is too short, enter 4 symbols at least";
            req.setAttribute("message", message);
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
        } else if (user.getPassword().length() < 6) {
            message = "Password is too short, enter 6 symbols at least";
            req.setAttribute("message", message);
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
        } else {
            if (Database.contains(user)) {
                message = "User with name " + user.getName() + " already exists";
                req.setAttribute("message", message);
                req.getRequestDispatcher("registration.jsp").forward(req, resp);
            } else {
                Database.addUser(user);
                message = "Hello and welcome " + user.getName();
                req.setAttribute("message", message);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
        }
    }
}
