package mate.academy.servlets.admin.users;

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

public class EditServletTest {
    UserDao userDao;
    EditServlet editServlet;
    User userOne;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        editServlet = new EditServlet();
        userDao = new UserDaoHib();
        userOne = new User("111111", "111111", "test1@test.com");
        userDao.addUser(userOne);
    }

    @After
    public void clean() {
        userDao.removeUser(userOne);
    }

    @Test
    public void doPost() throws IOException, ServletException {
        Long id = userDao.getUserByLogin(userOne.getLogin()).get().getId();
        Mockito.when(request.getParameter("id")).thenReturn(id.toString());
        Mockito.when(request.getParameter("login")).thenReturn(userOne.getLogin());
        Mockito.when(request.getParameter("password")).thenReturn(userOne.getPassword());
        Mockito.when(request.getParameter("mail")).thenReturn(userOne.getMail());
        Mockito.when(request.getRequestDispatcher("/admin/list")).thenReturn(requestDispatcher);
        editServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/admin/list");
    }
}