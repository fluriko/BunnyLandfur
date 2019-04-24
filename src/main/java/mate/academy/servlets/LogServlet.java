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
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        User user = new User(name, password);
        if (checkUser(user).equals("welcomeback.jsp")) {
            req.setAttribute("name", name);
        }
        req.getRequestDispatcher(checkUser(user)).forward(req, resp);
    }

    public String checkUser(User user) {
        if (Database.contains(user)) {
            if (Database.getUser(user).getPassword().equals(user.getPassword())) {
                return "welcomeback.jsp";
            } else {
                return "wrongpass.jsp";
            }
        } else {
            return "notexist.jsp";
        }
    }
}
