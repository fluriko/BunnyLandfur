package mate.academy.servlets;

import mate.academy.database.Database;
import mate.academy.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/delete")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        User user = new User(name);
        Database.removeUser(user);
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        out.println("User " + name + " was deleted successfully!<br /><br />");
        out.println("<a href='list'>Back list of users</a><br /><br />");
        out.println("<a href='index.jsp'>Back to main</a><br /><br />");
    }
}
