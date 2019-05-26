package mate.academy.servlets.user;

import mate.academy.model.Order;
import mate.academy.model.User;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/user/orders")
public class UserOrdersServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserOrdersServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        logger.debug(user.getInfo() + " is on orders page");
        List<Order> orders = user.getOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/user/orders.jsp").forward(request, response);
    }
}
