package mate.academy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LogServletTest {
    User userOk;
    User userWrong;
    User userNotExist;
    LogServlet logServlet;

    @Before
    public void initialization() {
        logServlet = new LogServlet();
        userOk = new User("fluriko", "123456");
        userWrong = new User("fluriko", "1234");
        userNotExist = new User("freya", "123456");
        User.addUser(userOk);
    }

    @Test
    public void checkUserOk() {
        String expected = logServlet.checkUser(userOk);
        String actual = "welcomeback.jsp";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void checkUserWrong() {
        String expected = logServlet.checkUser(userWrong);
        String actual = "wrongpass.jsp";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void checkUserNotExist() {
        String expected = logServlet.checkUser(userNotExist);
        String actual = "notexist.jsp";
        Assert.assertEquals(actual, expected);
    }
}