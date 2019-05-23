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

@WebServlet(value = "/user/removeGood")
public class DeleteGoodServlet extends HttpServlet {
    private static final GoodDao goodDao = new GoodDaoHibImpl();
    private static final CartDao cartDao = new CartDaoHibImpl();
    private static final Logger logger = Logger.getLogger(DeleteGoodServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Long goodId = Long.parseLong(request.getParameter("goodId"));
        Good good = goodDao.get(goodId).get();
        user.removeFromCart(good);
        cartDao.edit(user.getCart());
        logger.debug(user.getInfo() + " deleted " + good.getId() + " from his cart");
        request.setAttribute("message", good.getLabel() + " was removed from cart");
        request.getRequestDispatcher("/user/cart").forward(request, response);
    }
}
