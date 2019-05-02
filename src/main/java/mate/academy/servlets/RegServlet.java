package mate.academy.servlets;

import mate.academy.database.UserDao;
import mate.academy.model.User;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/registration")
public class RegServlet extends HttpServlet {
    private static final UserDao USER_DAO = new UserDao();
    private static final Logger logger = Logger.getLogger(RegServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("User started registration");
        req.setAttribute("message", "");
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name").trim();
        String password = req.getParameter("password").trim();
        String mail = req.getParameter("mail").trim();
        logger.debug("User entered data " + name + " " + password + " " + mail);
        String message;
        if (name.length() < 4) {
            logger.debug("Login " + name + " is too short");
            message = "Name " + name + " is too short, enter 4 symbols at least";
            req.setAttribute("message", message);
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
        } else if (password.length() < 6) {
            logger.debug("Password " + password + " is too short");
            message = "Password is too short, enter 6 symbols at least";
            req.setAttribute("message", message);
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
        } else if (!mail.endsWith("@gmail.com") || mail.length() < 16) {
            logger.debug("Mail " + mail + " is not correct");
            message = "Your mail should be real and end with @gmail.com";
            req.setAttribute("message", message);
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
        } else {
            logger.debug("Data is correct");
            if (USER_DAO.contains(name)) {
                logger.debug("User tried to register with existing name");
                message = "User with name " + name + " already exists";
                req.setAttribute("message", message);
                req.getRequestDispatcher("registration.jsp").forward(req, resp);
            } else if (USER_DAO.containsMail(mail)) {
                logger.debug("User tried to register with existing mail");
                message = "User with mail " + mail + " already exists";
                req.setAttribute("message", message);
                req.getRequestDispatcher("registration.jsp").forward(req, resp);
            }  else {
                User user = new User(name, password, mail);
                USER_DAO.addUser(user);
                User userGet = USER_DAO.getUser(name).get();
                logger.debug("User " + userGet.getId() + " registered successfully");
                message = "Hello and welcome " + name + ", now you can log in";
                req.setAttribute("message", message);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
        }
    }
}
