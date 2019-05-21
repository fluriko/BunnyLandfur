package mate.academy.servlets.user;

import mate.academy.database.CartDao;
import mate.academy.database.PurchaseCodeDao;
import mate.academy.database.impl.CartDaoHibImpl;
import mate.academy.database.impl.PurchaseCodeDaoHibImpl;
import mate.academy.model.Code;
import mate.academy.model.User;
import mate.academy.service.MailService;
import mate.academy.util.PurchaseCodeCleaner;
import mate.academy.util.RandomGenerator;
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
    private static final PurchaseCodeDao purchaseCodeDao = new PurchaseCodeDaoHibImpl();
    private static final CartDao cartDao = new CartDaoHibImpl();
    private static final MailService mailService = new MailService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codeValue = request.getParameter("codeValue");
        User user = (User) request.getSession().getAttribute("user");
        Long codeId = Long.parseLong(request.getParameter("codeId"));
        Code code = new Code(codeId, codeValue, user.getCart());
        Code codeFromDb = purchaseCodeDao.get(codeId).get();
        if (codeFromDb.equals(code)) {
            logger.info(user.getInfo() + " paid the purchase " + user.getCart());
            request.setAttribute("message", "successful purchase");
            user.cleanCart();
            cartDao.edit(user.getCart());
        } else {
            request.setAttribute("message", "you entered wrong code");
        }
        purchaseCodeDao.remove(code);
        request.getRequestDispatcher("/user/result.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String codeValue = RandomGenerator.generateCode();
        while (purchaseCodeDao.getCodeByValue(codeValue).isPresent()) {
            codeValue = RandomGenerator.generateCode();
        }
        mailService.sendMail(user.getMail(), codeValue);
        Code code = new Code(codeValue, user.getCart());
        purchaseCodeDao.add(code);
        Long codeId = purchaseCodeDao.getCodeByValue(codeValue).get().getId();
        PurchaseCodeCleaner.clean(code);
        request.setAttribute("codeId", codeId);
        request.getRequestDispatcher("/user/buy.jsp").forward(request, response);
    }
}
