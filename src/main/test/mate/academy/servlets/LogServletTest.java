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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogServletTest {
    UserDao userDao;
    LogServlet logServlet;
    User userOne;
    User userTwo;
    User fluriko;

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
        logServlet = new LogServlet();
        userDao = new UserDaoHib();
        userOne = new User("111111", "111111", "test1Log@test.com");
        userTwo = new User("222222", "222222", "test2Log@test.com");
        fluriko = userDao.getUserByLogin("fluriko").get();
    }

    @After
    public void clean() {
        userDao.removeUser(userOne);
        userDao.removeUser(userTwo);
    }

    @Test
    public void doPostNotExist() throws IOException, ServletException {
        Mockito.when(request.getParameter("login")).thenReturn(userOne.getLogin());
        Mockito.when(request.getParameter("password")).thenReturn(userOne.getPassword());
        Mockito.when(request.getRequestDispatcher("/login.jsp")).thenReturn(requestDispatcher);
        logServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/login.jsp");
    }

    @Test
    public void doPostWrongPass() throws IOException, ServletException {
        userDao.addUser(userOne);
        Mockito.when(request.getParameter("login")).thenReturn(userOne.getLogin());
        Mockito.when(request.getParameter("password")).thenReturn("1111");
        Mockito.when(request.getRequestDispatcher("/login.jsp")).thenReturn(requestDispatcher);
        logServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/login.jsp");
    }

    @Test
    public void doPostUser() throws IOException, ServletException {
        userDao.addUser(userTwo);
        Mockito.when(request.getParameter("login")).thenReturn(userTwo.getLogin());
        Mockito.when(request.getParameter("password")).thenReturn("222222");
        Mockito.when(request.getRequestDispatcher("/user/goods")).thenReturn(requestDispatcher);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession().getAttribute("user")).thenReturn(userTwo);
        logServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/user/goods");
    }

    @Test
    public void doPostAdmin() throws IOException, ServletException {
        Mockito.when(request.getParameter("login")).thenReturn("fluriko");
        Mockito.when(request.getParameter("password")).thenReturn("123123");
        Mockito.when(request.getRequestDispatcher("/admin")).thenReturn(requestDispatcher);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession().getAttribute("user")).thenReturn(fluriko);
        logServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/admin");
    }
}