package mate.academy.servlets;

import mate.academy.database.UserDao;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogServletTest {
    private static final UserDao USER_DAO = new UserDao();
    LogServlet logServlet;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    HttpSession session;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        USER_DAO.removeAll();
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
        User user = new User("betta", "111qqq", "insania@gmail.com");
        USER_DAO.addUser(user);
        Mockito.when(request.getParameter("name")).thenReturn("betta");
        Mockito.when(request.getParameter("password")).thenReturn("111qqq");
        Mockito.when(request.getRequestDispatcher("/goods")).thenReturn(requestDispatcher);
        Mockito.when(request.getSession()).thenReturn(session);
        logServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/goods");
    }

    @Test
    public void doPostWrong() throws IOException, ServletException {
        User user = new User("betta", "111qqq", "insania@gmail.com");
        USER_DAO.addUser(user);
        Mockito.when(request.getParameter("name")).thenReturn("betta");
        Mockito.when(request.getParameter("password")).thenReturn("222www");
        Mockito.when(request.getRequestDispatcher("login.jsp")).thenReturn(requestDispatcher);
        logServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("login.jsp");
    }
}