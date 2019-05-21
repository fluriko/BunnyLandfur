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

@WebServlet(value = "/admin/editGood")
public class EditGoodServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(EditGoodServlet.class);
    private static final GoodDao goodDao = new GoodDaoHibImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long goodId = Long.parseLong(req.getParameter("id"));
        Good good = goodDao.get(goodId).get();
        req.setAttribute("good", good);
        req.getRequestDispatcher("/admin/editGood.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long goodId = Long.parseLong(req.getParameter("id"));
        Good goodToEdit = goodDao.get(goodId).get();
        String oldGoodInfo = goodToEdit.toString();
        String newLabel = req.getParameter("label").trim();
        String newDescription = req.getParameter("description").trim();
        String newCategory = req.getParameter("category").trim();
        Double newPrice = Double.parseDouble(req.getParameter("price"));
        if (newLabel.length() >= 3) {
            goodToEdit.setLabel(newLabel);
        }
        if (newDescription.length() >= 5) {
            goodToEdit.setDescription(newDescription);
        }
        if (newCategory.length() >= 3) {
            goodToEdit.setCategory(newCategory);
        }
        if (newPrice > 0) {
            goodToEdit.setPrice(newPrice);
        }
        goodDao.edit(goodToEdit);
        String message = "Good was edited: " + goodId;
        User admin = (User) req.getSession().getAttribute("user");
        logger.info(admin.getInfo() + " edited good " + goodId);
        logger.info("Old fields: " + oldGoodInfo);
        req.setAttribute("message", message);
        req.getRequestDispatcher("/admin/goods").forward(req, resp);
    }
}
