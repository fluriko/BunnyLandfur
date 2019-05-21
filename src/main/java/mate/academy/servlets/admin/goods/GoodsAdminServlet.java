package mate.academy.servlets.admin.goods;

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

@WebServlet(value = "/admin/goods")
public class GoodsAdminServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GoodsAdminServlet.class);
    private static final GoodDao goodDao = new GoodDaoHibImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User admin = (User) request.getSession().getAttribute("user");
        logger.info(admin.getInfo() + " is on goods page");
        List<Good> goods = goodDao.getAll();
        request.setAttribute("goods", goods);
        request.getRequestDispatcher("/admin/goods.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
