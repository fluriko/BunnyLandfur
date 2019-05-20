package mate.academy.servlets.user;

import mate.academy.database.cart.CartDao;
import mate.academy.database.cart.CartDaoHib;
import mate.academy.database.good.GoodDao;
import mate.academy.database.good.GoodDaoHib;
import mate.academy.model.Good;
import mate.academy.model.User;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/user/changeQuantity")
public class EditQuantityServlet extends HttpServlet {
    private static final GoodDao GOOD_DAO = new GoodDaoHib();
    private static final CartDao CART_DAO = new CartDaoHib();
    private static final Logger logger = Logger.getLogger(EditQuantityServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Long goodId = Long.parseLong(request.getParameter("goodId"));
        Long quantity = Long.parseLong(request.getParameter("quantity"));
        Good good = GOOD_DAO.getGood(goodId).get();
        good.setQuantity(quantity);
        user.editGoodInCart(good);
        CART_DAO.editCart(user.getCart());
        logger.debug("User edited quantity for " + good.getId());
        request.getRequestDispatcher("/user/cart").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long goodId = Long.parseLong(request.getParameter("goodId"));
        request.setAttribute("goodId", goodId);
        request.getRequestDispatcher("/user/changeQuantity.jsp").forward(request, response);
    }
}
