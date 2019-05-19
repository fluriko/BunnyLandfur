package mate.academy.servlets.user;

import mate.academy.database.cart.CartDao;
import mate.academy.database.cart.CartDaoHib;
import mate.academy.model.User;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/user/cleanCart")
public class CleanCartServlet extends HttpServlet {
    private static final CartDao CART_DAO = new CartDaoHib();
    private static final Logger logger = Logger.getLogger(CleanCartServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        user.cleanCart();
        CART_DAO.editCart(user.getCart());
        logger.debug(user.getId() + " cleaned cart");
        request.setAttribute("message", "Cart is clean");
        request.getRequestDispatcher("/user/goods").forward(request, response);
    }
}
