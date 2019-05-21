package mate.academy.servlets.user;

import mate.academy.database.CartDao;
import mate.academy.database.impl.CartDaoHibImpl;
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
    private static final CartDao cartDao = new CartDaoHibImpl();
    private static final Logger logger = Logger.getLogger(CleanCartServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        user.cleanCart();
        cartDao.edit(user.getCart());
        logger.debug(user.getInfo() + " cleaned his cart");
        request.setAttribute("message", "Cart is clean");
        request.getRequestDispatcher("/user/goods").forward(request, response);
    }
}
