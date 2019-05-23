package mate.academy.servlets.user;

import mate.academy.database.GoodDao;
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
import java.util.List;

@WebServlet(value = "/user/goods")
public class GoodsServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GoodsServlet.class);
    private static final GoodDao goodDao = new GoodDaoHibImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        logger.debug(user.getInfo() + " is on goods page");
        List<Good> goods = goodDao.getAll();
        request.setAttribute("goods", goods);
        request.setAttribute("cartInfo", user.getGoodsInCart().size());
        request.getRequestDispatcher("/user/goods.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
