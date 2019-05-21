package mate.academy.servlets;

import mate.academy.database.UserDao;
import mate.academy.database.impl.UserDaoHibImpl;
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
    private static final UserDao userDao = new UserDaoHibImpl();
    private static final Logger logger = Logger.getLogger(RegServlet.class);
    private static final String INSTRUCTION = "login should be 4 chars at least,\n" +
            "password should be 6 chars at least,\n" +
            "mail should be real and end with @gmail.com.\n";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Guest started registration");
        req.setAttribute("instruction", INSTRUCTION);
        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (checkCorrect(req, resp) && checkUnique(req, resp)) {
            addUser(req, resp);
        } else {
            logger.debug("Registration failed");
        }
    }

    private boolean checkCorrect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return checkCorrectLog(req, resp)
                && checkCorrectPass(req, resp)
                &&checkCorrectMail(req, resp);
    }

    private boolean checkUnique(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return checkUniqueLog(req, resp)
                && checkUniqueMail(req, resp);
    }

    private boolean checkCorrectLog(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").trim();
        if (login.length() < 4) {
            String message = "Login " + login + " is too short, enter 4 symbols at least\n";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
            return false;
        }
        return true;
    }

    private boolean checkCorrectPass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password").trim();
        if (password.length() < 6) {
            String message = "Password is too short, enter 6 symbols at least\n";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
            return false;
        }
        return true;
    }

    private boolean checkCorrectMail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mail = req.getParameter("mail").trim();
        if (!mail.endsWith("@gmail.com") || mail.length() < 16) {
            String message = "Your mail should be real and end with @gmail.com\n";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
            return false;
        }
        return true;
    }

    private boolean checkUniqueLog(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").trim();
        if (userDao.getUserByLogin(login).isPresent()) {
            String message = "User with name " + login + " already exists";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
            return false;
        }
        return true;
    }

    private boolean checkUniqueMail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mail = req.getParameter("mail").trim();
        if (userDao.getUserByMail(mail).isPresent()) {
            String message = "User with mail " + mail + " already exists";
            req.setAttribute("message", message);
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
            return false;
        }
        return true;
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").trim();
        String password = req.getParameter("password").trim();
        String mail = req.getParameter("mail").trim();
        User user = new User(login, password, mail);
        userDao.add(user);
        User userJustRegistered = userDao.getUserByLogin(login).get();
        logger.info(userJustRegistered.getInfo() + " registered successfully");
        req.getRequestDispatcher("/login").forward(req, resp);
    }
}
