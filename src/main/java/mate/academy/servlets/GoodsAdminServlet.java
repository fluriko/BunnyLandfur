package mate.academy.servlets;

import mate.academy.database.good.GoodDao;
import mate.academy.database.good.GoodDaoHib;
import mate.academy.database.good.GoodDaoJdbc;
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

@WebServlet(value = "/admin/goods")
public class GoodsAdminServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GoodsAdminServlet.class);
    private static final GoodDao GOOD_DAO = new GoodDaoHib();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        logger.info("Admin " + user.getLogin() + " is on goods page");
        List<Good> goods = GOOD_DAO.getGoods();
        request.setAttribute("goods", goods);
        request.getRequestDispatcher("/admin/goods.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
