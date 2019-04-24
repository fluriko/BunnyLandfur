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
import java.util.List;

@WebServlet(value = "/list")
public class ListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        out.println("<h1>List of users</h1>");
        List<User> users = Database.getUsers();
        out.print("<table border='1' width='100%'");
        out.print("<tr><th>Name</th><th>Password</th><th>Edit</th><th>Delete</th></tr>");
        for(User user:users){
            out.print("<tr><td>"+user.getName() + "</td>" +
                    "<td>"+user.getPassword() + "</td>" +
                    "<td><a href='edit?name=" + user.getName() +"'>edit</a></td>" +
                    "<td><a href='delete?name=" + user.getName() +"'>delete</a></td></tr>");
        }
        out.print("</table>");
        out.close();
    }
}
