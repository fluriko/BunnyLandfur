package mate.academy.servlets;

import mate.academy.database.good.GoodDao;
import mate.academy.database.good.GoodDaoHib;
import mate.academy.model.Good;
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
    private static final GoodDao GOOD_DAO = new GoodDaoHib();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Admin started edit good page");
        Long goodId = Long.parseLong(req.getParameter("id"));
        Good good = GOOD_DAO.getGood(goodId).get();
        req.setAttribute("good", good);
        req.getRequestDispatcher("/admin/editGood.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long goodId = Long.parseLong(req.getParameter("id"));
        Good goodToEdit = GOOD_DAO.getGood(goodId).get();
        String newLabel = req.getParameter("label").trim();
        String newDescription = req.getParameter("description").trim();
        String newCategory = req.getParameter("category").trim();
        Double newPrice;
        try {
            newPrice = Double.parseDouble(req.getParameter("price"));
        } catch (Exception e) {
            newPrice = 0.0;
        }
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
        String message = "Good was edited: " + goodId;
        logger.info(message);
        GOOD_DAO.editGood(goodToEdit);
        req.setAttribute("message", message);
        req.getRequestDispatcher("/admin/goods").forward(req, resp);
    }
}
