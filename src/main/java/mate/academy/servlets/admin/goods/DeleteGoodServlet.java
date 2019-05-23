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

@WebServlet(value = "/admin/deleteGood")
public class DeleteGoodServlet extends HttpServlet {
    private static final GoodDao goodDao = new GoodDaoHibImpl();
    private static final Logger logger = Logger.getLogger(DeleteGoodServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long goodId = Long.parseLong(request.getParameter("id"));
        Good good = goodDao.get(goodId).get();
        goodDao.remove(good);
        String message = "Good " + goodId + " was deleted successfully!";
        User admin = (User) request.getSession().getAttribute("user");
        logger.info(admin.getInfo() + " deleted good " + good);
        request.setAttribute("message", message);
        request.getRequestDispatcher("/admin/goods").forward(request, response);
    }
}
