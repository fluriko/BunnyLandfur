package mate.academy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/login")
public class LogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = User.getUsers();
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        User user = new User(name, password);
        if (checkUser(user).equals("welcomeback.jsp")) {
            req.setAttribute("name", name);
        }
        req.getRequestDispatcher(checkUser(user)).forward(req, resp);
    }

    protected String checkUser(User user) {
        List<User> users = User.getUsers();
        if (users.contains(user)) {
            if (User.getUser(user).getPassword().equals(user.getPassword())) {
                return "welcomeback.jsp";
            } else {
                return "wrongpass.jsp";
            }
        } else {
            return "notexist.jsp";
        }
    }
}
