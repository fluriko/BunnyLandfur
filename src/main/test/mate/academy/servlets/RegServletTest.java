package mate.academy.servlets;

import mate.academy.database.user.UserDao;
import mate.academy.database.user.UserDaoHib;
import mate.academy.model.User;
import org.junit.After;
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
import java.util.Optional;

public class RegServletTest {
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

    @After
    public void clean() {
        UserDao userDao = new UserDaoHib();
        Optional<User> optionalUser = userDao.getUserByLogin("111111");
//        optionalUser.ifPresent((o) -> userDao.removeUser(o));
        if (optionalUser.isPresent()) {
            userDao.removeUser(optionalUser.get());
        }
    }

    @Test
    public void doPostAdd() throws IOException, ServletException {
        Mockito.when(request.getParameter("login")).thenReturn("111111");
        Mockito.when(request.getParameter("password")).thenReturn("111111");
        Mockito.when(request.getParameter("mail")).thenReturn("test11@gmail.com");
        Mockito.when(request.getRequestDispatcher("/index.jsp")).thenReturn(requestDispatcher);
        regServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/index.jsp");
    }

    @Test
    public void doPostExistLog() throws IOException, ServletException {
        Mockito.when(request.getParameter("login")).thenReturn("fluriko");
        Mockito.when(request.getParameter("password")).thenReturn("222222");
        Mockito.when(request.getParameter("mail")).thenReturn("newuniq@gmail.com");
        Mockito.when(request.getRequestDispatcher("/registration.jsp")).thenReturn(requestDispatcher);
        regServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/registration.jsp");
    }

    @Test
    public void doPostExistMail() throws IOException, ServletException {
        Mockito.when(request.getParameter("login")).thenReturn("somerandomlogin");
        Mockito.when(request.getParameter("password")).thenReturn("222222");
        Mockito.when(request.getParameter("mail")).thenReturn("fluricode@gmail.com");
        Mockito.when(request.getRequestDispatcher("/registration.jsp")).thenReturn(requestDispatcher);
        regServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/registration.jsp");
    }

    @Test
    public void doPostIncorrectPass() throws IOException, ServletException {
        Mockito.when(request.getParameter("login")).thenReturn("222222");
        Mockito.when(request.getParameter("password")).thenReturn("1");
        Mockito.when(request.getParameter("mail")).thenReturn("test22@gmail.com");
        Mockito.when(request.getRequestDispatcher("/registration.jsp")).thenReturn(requestDispatcher);
        regServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/registration.jsp");
    }

    @Test
    public void doPostIncorrectLog() throws IOException, ServletException {
        Mockito.when(request.getParameter("login")).thenReturn("g");
        Mockito.when(request.getParameter("password")).thenReturn("222222");
        Mockito.when(request.getParameter("mail")).thenReturn("test22@gmail.com");
        Mockito.when(request.getRequestDispatcher("/registration.jsp")).thenReturn(requestDispatcher);
        regServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/registration.jsp");
    }

    @Test
    public void doPostIncorrectMail() throws IOException, ServletException {
        Mockito.when(request.getParameter("login")).thenReturn("222222");
        Mockito.when(request.getParameter("password")).thenReturn("222222");
        Mockito.when(request.getParameter("mail")).thenReturn("test2@test.com");
        Mockito.when(request.getRequestDispatcher("registration.jsp")).thenReturn(requestDispatcher);
        regServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("registration.jsp");
    }
}