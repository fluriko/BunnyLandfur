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
import java.io.IOException;

public class EditServletTest {
    private static final UserDao USER_DAO = new UserDao();
    EditServlet editServlet;

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
        User user = new User("alpha", "111qqq");
        USER_DAO.addUser(user);
    }

    @Test
    public void doPostShort() throws IOException, ServletException {
        Mockito.when(request.getParameter("name")).thenReturn("alpha");
        Mockito.when(request.getParameter("password")).thenReturn("12");
        Mockito.when(request.getRequestDispatcher("edit.jsp")).thenReturn(requestDispatcher);
        editServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("edit.jsp");
    }

    @Test
    public void doPostOk() throws IOException, ServletException {
        Mockito.when(request.getParameter("name")).thenReturn("alpha");
        Mockito.when(request.getParameter("password")).thenReturn("111qqq");
        Mockito.when(request.getRequestDispatcher("/admin")).thenReturn(requestDispatcher);
        editServlet.doPost(request, response);
        Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/admin");
    }
}