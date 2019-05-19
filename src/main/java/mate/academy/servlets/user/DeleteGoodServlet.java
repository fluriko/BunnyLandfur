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

@WebServlet(value = "/user/removeGood")
public class DeleteGoodServlet extends HttpServlet {
    private static final GoodDao GOOD_DAO = new GoodDaoHib();
    private static final CartDao CART_DAO = new CartDaoHib();
    private static final Logger logger = Logger.getLogger(DeleteGoodServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Long id = Long.parseLong(request.getParameter("goodId"));
        Good good = GOOD_DAO.getGood(id).get();
        user.removeFromCart(good);
        CART_DAO.editCart(user.getCart());
        logger.debug("User deleted " + good.getId() + " from cart");
        request.setAttribute("message", good.getLabel() + " was removed from cart");
        request.getRequestDispatcher("/user/cart").forward(request, response);
    }
}
