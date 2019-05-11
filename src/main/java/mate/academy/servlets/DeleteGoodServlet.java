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

@WebServlet(value = "/admin/deleteGood")
public class DeleteGoodServlet extends HttpServlet {
    private static final GoodDao GOOD_DAO = new GoodDaoHib();
    private static final Logger logger = Logger.getLogger(DeleteGoodServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Good good = GOOD_DAO.getGood(id).get();
        logger.debug("Admin tried to delete good " + id);
        String message;
        if (GOOD_DAO.removeGood(good) == 0L) {
            logger.debug("Admin can't delete good " + id);
            message = "Fail in deleting good " + id;
        } else {
            logger.info("Admin deleted good " + id);
            message = "Good " + id + " was deleted successfully!";
        }
        req.setAttribute("message", message);
        req.getRequestDispatcher("/admin/deleteGood.jsp").forward(req, resp);
    }
}
