package mate.academy.servlets;

import mate.academy.database.good.GoodDao;
import mate.academy.database.good.GoodDaoHib;
import mate.academy.database.good.GoodDaoJdbc;
import mate.academy.model.Good;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/admin/addGood")
public class AddGoodServlet extends HttpServlet {
    private static final GoodDao GOOD_DAO = new GoodDaoHib();
    private static final Logger logger = Logger.getLogger(DeleteGoodServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String label = request.getParameter("label").trim();
        String description = request.getParameter("description").trim();
        String category = request.getParameter("category").trim();
        String priceString = request.getParameter("price");
        Double price;
        try {
            price = Double.parseDouble(priceString);
        } catch (Exception e) {
            price = 0.0;
            logger.debug("Admin entered not acceptable price");
        }
        String message;
        if (price <= 0) {
            message = "You entered wrong price";
            logger.debug(message);
            request.setAttribute("message", message);
            request.getRequestDispatcher("addGood.jsp").forward(request, response);
        }
        if (category.length() < 3) {
            message = "Category is too short";
            logger.debug(message);
            request.setAttribute("message", message);
            request.getRequestDispatcher("addGood.jsp").forward(request, response);
        }
        if (label.length() < 3) {
            message = "Label is too short";
            logger.debug(message);
            request.setAttribute("message", message);
            request.getRequestDispatcher("addGood.jsp").forward(request, response);
        }
        Good good = new Good(label, description, category, price);
        GOOD_DAO.addGood(good);
        message = "successfully added good: " + label;
        logger.debug(message);
        request.setAttribute("message", message);
        request.getRequestDispatcher("/admin/goods").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Admin tried to add good");
        request.getRequestDispatcher("/admin/addGood.jsp").forward(request, response);
    }
}
