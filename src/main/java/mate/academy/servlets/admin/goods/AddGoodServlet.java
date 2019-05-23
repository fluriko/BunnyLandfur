package mate.academy.servlets.admin.goods;

import mate.academy.database.GoodDao;
import mate.academy.database.impl.GoodDaoHibImpl;
import mate.academy.model.Good;
import mate.academy.model.User;
import mate.academy.service.validator.GenericValidationService;
import mate.academy.service.validator.GoodValidationService;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/admin/addGood")
public class AddGoodServlet extends HttpServlet {
    private static final GoodDao goodDao = new GoodDaoHibImpl();
    private static final GenericValidationService validationService = new GoodValidationService();
    private static final Logger logger = Logger.getLogger(AddGoodServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String label = request.getParameter("label").trim();
        String description = request.getParameter("description").trim();
        String category = request.getParameter("category").trim();
        Double price = Double.parseDouble(request.getParameter("price"));
        Good good = new Good(label, description, category, price);
        String violations = validationService.validate(good);
        if (violations.isEmpty()) {
            goodDao.add(good);
            String message = "Good " + label + " added successfully!";
            User admin = (User) request.getSession().getAttribute("user");
            logger.debug(admin.getInfo() + "added good " + label);
            request.setAttribute("message", message);
            request.getRequestDispatcher("/admin/goods").forward(request, response);
        } else {
            logger.debug("Fail in adding good: " + violations);
            request.setAttribute("violations", violations);
            doGet(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/addGood.jsp").forward(request, response);
    }
}
