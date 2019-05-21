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

@WebServlet(value = "/admin/addGood")
public class AddGoodServlet extends HttpServlet {
    private static final GoodDao goodDao = new GoodDaoHibImpl();
    private static final Logger logger = Logger.getLogger(AddGoodServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String label = request.getParameter("label").trim();
        String description = request.getParameter("description").trim();
        String category = request.getParameter("category").trim();
        Double price = Double.parseDouble(request.getParameter("price"));
        String message;
        if (price <= 0) {
            message = "Price can't be 0 or negative. ";
            request.setAttribute("message", message);
            request.getRequestDispatcher("/admin/addGood.jsp").forward(request, response);
        }
        if (category.length() < 3) {
            message = "Category is too short. ";
            request.setAttribute("message", message);
            request.getRequestDispatcher("/admin/addGood.jsp").forward(request, response);
        }
        if (label.length() < 3) {
            message = "Label is too short. ";
            request.setAttribute("message", message);
            request.getRequestDispatcher("/admin/addGood.jsp").forward(request, response);
        }
        Good good = new Good(label, description, category, price);
        goodDao.add(good);
        message = "Good " + label + " added successfully!";
        User admin = (User) request.getSession().getAttribute("user");
        logger.debug(admin.getInfo() + "added good " + label);
        request.setAttribute("message", message);
        request.getRequestDispatcher("/admin/goods").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/addGood.jsp").forward(request, response);
    }
}
