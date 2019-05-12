package mate.academy.servlets;

import mate.academy.database.user.UserDao;
import mate.academy.database.user.UserDaoJdbc;
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

public class RegServletTest {
    private static final UserDao USER_DAO = new UserDaoJdbc();
    RegServlet regServlet;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        regServlet = new RegServlet();
    }

    @Test
    public void doPostHello() throws IOException, ServletException {
        Mockito.when(request.getParameter("name")).thenReturn("alpha");
        Mockito.when(request.getParameter("password")).thenReturn("111qqq");
        Mockito.when(request.getRequestDispatcher("index.jsp")).thenReturn(requestDispatcher);
        regServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("index.jsp");
    }

    @Test
    public void doPostExist() throws IOException, ServletException {
        User user = new User("betta", "111qqq", "bettaa@gmail.com", "salt");
        USER_DAO.addUser(user);
        Mockito.when(request.getParameter("name")).thenReturn("betta");
        Mockito.when(request.getParameter("password")).thenReturn("111qqq");
        Mockito.when(request.getRequestDispatcher("registration.jsp")).thenReturn(requestDispatcher);
        regServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("registration.jsp");
    }

    @Test
    public void doPostPass() throws IOException, ServletException {
        Mockito.when(request.getParameter("name")).thenReturn("gamma");
        Mockito.when(request.getParameter("password")).thenReturn("1");
        Mockito.when(request.getRequestDispatcher("registration.jsp")).thenReturn(requestDispatcher);
        regServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("registration.jsp");
    }

    @Test
    public void doPostLog() throws IOException, ServletException {
        Mockito.when(request.getParameter("name")).thenReturn("g");
        Mockito.when(request.getParameter("password")).thenReturn("111qqq");
        Mockito.when(request.getRequestDispatcher("registration.jsp")).thenReturn(requestDispatcher);
        regServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("registration.jsp");
    }
}