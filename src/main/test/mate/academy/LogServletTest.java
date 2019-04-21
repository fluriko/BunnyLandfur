package mate.academy;

import org.junit.Test;

import static org.junit.Assert.*;

public class LogServletTest {
    @Test
    public void checkUser() {
        User userOk = new User("fluriko", "123456");
        User userWrong = new User("fluriko", "1234");
        User userNotExist = new User("freya", "123456");
        User.addUser(userOk);
        LogServlet logServlet = new LogServlet();
        String expected = logServlet.checkUser(userOk);
        String actual = "welcomeback.jsp";
        assertEquals(actual, expected);
        expected = logServlet.checkUser(userWrong);
        actual = "wrongpass.jsp";
        assertEquals(actual, expected);
        expected = logServlet.checkUser(userNotExist);
        actual = "notexist.jsp";
        assertEquals(actual, expected);
    }
}