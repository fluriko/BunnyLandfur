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

@WebServlet(value = "/edit")
public class EditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        req.getRequestDispatcher("edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String name = req.getParameter("name");
        String newPass = req.getParameter("password");
        if (newPass.length() < 6) {
            out.println("You entered too short password!<br /><br />");
            resp.setContentType("text/html");
            out.println("<a href='list'>Back to list of users</a><br /><br />");
            out.println("<a href='index.jsp'>Back to main</a><br /><br />");
        } else {
            Database.editUser(new User(name), newPass);
            resp.setContentType("text/html");
            out.println("User " + name + " was edited successfully!<br /><br />");
            out.println("<a href='list'>Back to list of users</a><br /><br />");
            out.println("<a href='index.jsp'>Back to main</a><br /><br />");
        }
    }
}
