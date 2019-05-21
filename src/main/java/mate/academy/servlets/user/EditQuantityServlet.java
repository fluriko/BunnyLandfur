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

@WebServlet(value = "/user/changeQuantity")
public class EditQuantityServlet extends HttpServlet {
    private static final GoodDao goodDao = new GoodDaoHibImpl();
    private static final CartDao cartDao = new CartDaoHibImpl();
    private static final Logger logger = Logger.getLogger(EditQuantityServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Long goodId = Long.parseLong(request.getParameter("goodId"));
        Long quantity = Long.parseLong(request.getParameter("quantity"));
        Good good = goodDao.get(goodId).get();
        good.setQuantity(quantity);
        user.editGoodInCart(good);
        cartDao.edit(user.getCart());
        logger.debug(user.getInfo() + " edited quantity for " + good.getId());
        request.getRequestDispatcher("/user/cart").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long goodId = Long.parseLong(request.getParameter("goodId"));
        request.setAttribute("goodId", goodId);
        request.getRequestDispatcher("/user/changeQuantity.jsp").forward(request, response);
    }
}
