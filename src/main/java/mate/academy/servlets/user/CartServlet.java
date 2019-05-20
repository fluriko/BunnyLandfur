package mate.academy.servlets.user;

import mate.academy.model.Good;
import mate.academy.model.User;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/user/cart")
public class CartServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CartServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        logger.debug(user.getId() + " on cart page");
        List<Good> goodsInCart = user.getGoodsInCart();
        request.setAttribute("goodsInCart", goodsInCart);
        double totalPrice = user.getCart().getGoodsInCart().stream().mapToDouble((o) -> o.getTotalPrice()).sum();
        request.setAttribute("total", totalPrice);
        request.getRequestDispatcher("/user/cart.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
