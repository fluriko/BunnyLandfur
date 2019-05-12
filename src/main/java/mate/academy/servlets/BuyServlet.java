package mate.academy.servlets;

import mate.academy.database.good.GoodDao;
import mate.academy.database.good.GoodDaoHib;
import mate.academy.database.good.GoodDaoJdbc;
import mate.academy.database.good.PurchaseCodeDao;
import mate.academy.database.good.PurchaseCodeDaoHib;
import mate.academy.database.good.PurchaseCodeDaoJdbc;
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
    private static final PurchaseCodeDao PURCHASE_CODE_DAO = new PurchaseCodeDaoHib();
    private static final GoodDao GOOD_DAO = new GoodDaoHib();
    private static final MailService MAIL_SERVICE = new MailService();
    private Good good;
    private int codeId;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codeValue = request.getParameter("codeValue");
        User user = (User) request.getSession().getAttribute("user");
        Code code = new Code(codeId, codeValue, user, good);
        Code codeFromDb = PURCHASE_CODE_DAO.getCode(codeId).get();
        if (codeFromDb.equals(code)) {
            request.setAttribute("message", "successful purchase");
        } else {
            request.setAttribute("message", "you entered wrong code");
        }
        PURCHASE_CODE_DAO.removeCode(code);
        good = null;
        codeId = 0;
        request.getRequestDispatcher("/user/result.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Long goodId = Long.parseLong(request.getParameter("goodId"));
        good = GOOD_DAO.getGood(goodId).get();
        logger.info("User " + user.getLogin() + " is on buy good " + goodId +" page");
        String codeValue = MAIL_SERVICE.sendMail(user.getMail());
        Code code = new Code(codeValue, user, good);
        PURCHASE_CODE_DAO.addCode(code);
        codeId = PURCHASE_CODE_DAO.getCode(codeValue).get().getId();
        PurchaseCodeCleaner.clean(code);
        request.getRequestDispatcher("/user/buy.jsp").forward(request, response);
    }
}
