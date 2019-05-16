package mate.academy.servlets;

import mate.academy.database.user.UserDao;
import mate.academy.database.user.UserDaoHib;
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
    private static final UserDao USER_DAO = new UserDaoHib();
    private static final Logger logger = Logger.getLogger(RegServlet.class);
    private static final String INSTRUCTION = "login should be 4 chars at least,\n" +
            "password should be 6 chars at least,\n" +
            "mail should be real and end with @gmail.com.\n";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("User started registration");
        req.setAttribute("instruction", INSTRUCTION);
        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkCorrect(req, resp);
        checkUnique(req, resp);
    }

    private void checkCorrect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkCorrectLog(req, resp);
        checkCorrectPass(req, resp);
        checkCorrectMail(req, resp);
    }

    private void checkUnique(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkUniqueLog(req, resp);
        checkUniqueMail(req, resp);
        addUser(req, resp);
    }

    private void checkCorrectLog(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").trim();
        if (login.length() < 4) {
            String message = "Login " + login + " is too short, enter 4 symbols at least\n";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
        }
    }

    private void checkCorrectPass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password").trim();
        if (password.length() < 6) {
            String message = "Password is too short, enter 6 symbols at least\n";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
        }
    }

    private void checkCorrectMail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mail = req.getParameter("mail").trim();
        if (!mail.endsWith("@gmail.com") || mail.length() < 16) {
            String message = "Your mail should be real and end with @gmail.com\n";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
        }
    }

    private void checkUniqueLog(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").trim();
        if (USER_DAO.getUserByLogin(login).isPresent()) {
            String message = "User with name " + login + " already exists";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
        }
    }

    private void checkUniqueMail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mail = req.getParameter("mail").trim();
        if (USER_DAO.getUserByMail(mail).isPresent()) {
            String message = "User with mail " + mail + " already exists";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
        }
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").trim();
        String password = req.getParameter("password").trim();
        String mail = req.getParameter("mail").trim();
        User user = new User(login, password, mail);
        USER_DAO.addUser(user);
        User userJustRegistered = USER_DAO.getUserByLogin(login).get();
        logger.info("User " + userJustRegistered.getId() + " registered successfully");
        String message = "Hello and welcome " + login + ", now you can log in";
        req.setAttribute("message", message);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
