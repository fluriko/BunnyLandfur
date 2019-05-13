package mate.academy.servlets;

import mate.academy.database.user.UserDao;
import mate.academy.database.user.UserDaoHib;
import mate.academy.database.user.UserDaoJdbc;
import mate.academy.model.User;
import mate.academy.util.HashUtil;
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
        String login = req.getParameter("login").trim();
        String password = req.getParameter("password").trim();
        String mail = req.getParameter("mail").trim();
        String checkLogCorrect = checkCorrect(login.length() < 4, "login");
        String checkPassCorrect = checkCorrect(password.length() < 6, "password");
        String checkMailCorrect = checkCorrect(!mail.endsWith("@gmail.com") || mail.length() < 16, "mail");
        if (checkLogCorrect.length() > 0 || checkPassCorrect.length() > 0 || checkMailCorrect.length() > 0) {
            String message = checkLogCorrect + checkPassCorrect + checkMailCorrect;
            req.setAttribute("message", message);
            req.setAttribute("instruction", INSTRUCTION);
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
        } else {
            String checkLogin = checkUniq(USER_DAO.getUserByLogin(login).isPresent(), "login " + login);
            String checkMail = checkUniq(USER_DAO.getUserByMail(mail).isPresent(), "mail " + mail);
            if (checkLogin.length() > 0 || checkMail.length() > 0) {
                String message = checkLogin + checkMail;
                req.setAttribute("message", message);
                req.getRequestDispatcher("/registration.jsp").forward(req, resp);
            } else {
                String salt = HashUtil.getRandomSalt();
                String hashPass = HashUtil.getSha512SecurePassword(password, salt);
                User user = new User(login, hashPass, mail, salt);
                USER_DAO.addUser(user);
                User userGet = USER_DAO.getUserByLogin(login).get();
                logger.debug("User " + userGet.getId() + " registered successfully");
                String message = "Hello and welcome " + login + ", now you can log in";
                req.setAttribute("message", message);
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        }
    }

    private String checkCorrect(boolean condition, String data) {
        String message = "";
        if (condition) {
            message = data + " is not correct!\n";
        }
        return message;
    }

    private String checkUniq(boolean condition, String data) {
        String message = "";
        if (condition) {
            message = "User with " + data + " already registered!\n";
        }
        return message;
    }
}
