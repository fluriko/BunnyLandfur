package mate.academy;

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
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        User user = new User(name, password);
        String page = checkUser(user);
        if (page.equals("greeting.jsp")) {
            req.setAttribute("name", name);
        }
        req.getRequestDispatcher(page).forward(req, resp);
    }

    protected String checkUser(User user) {
        if (user.getName().length() < 4 || user.getPassword().length() < 6) {
            return "wrongreg.jsp";
        } else {
            if (Database.contains(user)) {
                return "exist.jsp";
            } else {
                Database.addUser(user);
                return "greeting.jsp";
            }
        }
    }
}
