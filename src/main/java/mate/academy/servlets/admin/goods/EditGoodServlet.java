package mate.academy.servlets.admin.goods;

import mate.academy.database.GoodDao;
import mate.academy.database.impl.GoodDaoHibImpl;
import mate.academy.model.Good;
import mate.academy.model.User;
import mate.academy.service.validator.GoodValidationService;
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
    private static final GoodValidationService validationService = new GoodValidationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long goodId = Long.parseLong(request.getParameter("id"));
        Good good = goodDao.get(goodId).get();
        request.setAttribute("good", good);
        request.getRequestDispatcher("/admin/editGood.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long goodId = Long.parseLong(request.getParameter("id"));
        Good goodToEdit = goodDao.get(goodId).get();
        String oldGoodInfo = goodToEdit.toString();
        String newLabel = request.getParameter("label").trim();
        String newDescription = request.getParameter("description").trim();
        String newCategory = request.getParameter("category").trim();
        Double newPrice = Double.parseDouble(request.getParameter("price"));
        goodToEdit.setAll(newLabel, newDescription, newCategory, newPrice);
        String violations = validationService.validate(goodToEdit);
        if (violations.isEmpty()) {
            goodDao.edit(goodToEdit);
            String message = "Good was edited: " + goodId;
            User admin = (User) request.getSession().getAttribute("user");
            logger.info(admin.getInfo() + " edited good " + goodId);
            logger.info("Old fields: " + oldGoodInfo);
            request.setAttribute("message", message);
            request.getRequestDispatcher("/admin/goods").forward(request, response);
        } else {
            logger.debug("Fail in editing good: " + violations);
            request.setAttribute("violations", violations);
            doGet(request, response);
        }
    }
}
