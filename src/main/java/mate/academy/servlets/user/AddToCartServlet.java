package mate.academy.servlets.user;

import mate.academy.database.CartDao;
import mate.academy.database.GoodDao;
import mate.academy.database.impl.CartDaoHibImpl;
import mate.academy.database.impl.GoodDaoHibImpl;
import mate.academy.model.Good;
import mate.academy.model.User;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/user/addToCart")
public class AddToCartServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddToCartServlet.class);
    private static final GoodDao goodDao = new GoodDaoHibImpl();
    private static final CartDao cartDao = new CartDaoHibImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long goodId = Long.parseLong(request.getParameter("goodId"));
        Good good = goodDao.get(goodId).get();
        User user = (User) request.getSession().getAttribute("user");
        if (!user.getGoodsInCart().contains(good)) {
            user.addGoodToCart(good);
            cartDao.edit(user.getCart());
            logger.debug(user.getInfo() + " added good to his cart: " + goodId);
            request.setAttribute("message", "Good " + good.getLabel() + " added to cart!");
        } else {
            request.setAttribute("message", "Good " + good.getLabel() + " is already in cart!");
        }
        request.getRequestDispatcher("/user/goods").forward(request, response);
    }
}
