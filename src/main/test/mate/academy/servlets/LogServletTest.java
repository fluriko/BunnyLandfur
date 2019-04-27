package mate.academy.servlets;

import mate.academy.database.Database;
import mate.academy.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogServletTest {
    LogServlet logServlet;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Database.removeAll();
        logServlet = new LogServlet();
    }

    @Test
    public void doPostNotExist() throws IOException, ServletException {
        Mockito.when(request.getParameter("name")).thenReturn("alpha");
        Mockito.when(request.getParameter("password")).thenReturn("111qqq");
        Mockito.when(request.getRequestDispatcher("login.jsp")).thenReturn(requestDispatcher);
        logServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("login.jsp");
    }

    @Test
    public void doPostHello() throws IOException, ServletException {
        User user = new User("betta", "111qqq");
        Database.addUser(user);
        Mockito.when(request.getParameter("name")).thenReturn("betta");
        Mockito.when(request.getParameter("password")).thenReturn("111qqq");
        Mockito.when(request.getRequestDispatcher("index.jsp")).thenReturn(requestDispatcher);
        logServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("index.jsp");
    }

    @Test
    public void doPostWrong() throws IOException, ServletException {
        User user = new User("betta", "111qqq");
        Database.addUser(user);
        Mockito.when(request.getParameter("name")).thenReturn("betta");
        Mockito.when(request.getParameter("password")).thenReturn("222www");
        Mockito.when(request.getRequestDispatcher("login.jsp")).thenReturn(requestDispatcher);
        logServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("login.jsp");
    }
}