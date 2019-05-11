package mate.academy.servlets;

import mate.academy.database.good.GoodDaoJdbc;
import mate.academy.database.good.PurchaseCodeDao;
import mate.academy.model.Code;
import mate.academy.model.Good;
import mate.academy.model.User;
import mate.academy.service.MailService;
import mate.academy.util.PurchaseCodeCleaner;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/user/buy")
public class BuyServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(BuyServlet.class);
    private static final PurchaseCodeDao PURCHASE_CODE_DAO = new PurchaseCodeDao();
    private static final GoodDaoJdbc GOOD_DAO = new GoodDaoJdbc();
    private static final MailService MAIL_SERVICE = new MailService();
    private static Good good;
    private static int codeId;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codeValue = request.getParameter("codeValue");
        logger.info(codeValue);
        User user = (User) request.getSession().getAttribute("user");
        logger.info(user);
        logger.info(good);
        Code code = new Code(codeId, codeValue, user, good);
        if (PURCHASE_CODE_DAO.contains(code)) {
            request.setAttribute("message", "your purchase is successful");
        } else {
            request.setAttribute("message", "you entered wrong code");
        }
        PURCHASE_CODE_DAO.removeCodeById(codeId);
        good = null;
        codeId = 0;
        request.getRequestDispatcher("/user/result.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        logger.info(user);
        Long goodId = Long.parseLong(request.getParameter("goodId"));
        logger.info("goodId " + goodId);
        good = GOOD_DAO.getGood(goodId).get();
        logger.info(good);
        logger.info("User " + user.getLogin() + " is on buy good " + goodId +" page");
        String codeValue = MAIL_SERVICE.sendMail(user.getMail());
        logger.info(codeValue);
        Code code = new Code(codeValue, user, good);
        logger.info(code);
        codeId = PURCHASE_CODE_DAO.addCode(code);
        logger.info("codeId " + codeId);
        PurchaseCodeCleaner.clean(code);
        request.getRequestDispatcher("/user/buy.jsp").forward(request, response);
    }
}
