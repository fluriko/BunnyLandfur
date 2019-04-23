package mate.academy;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogServletTest {
    static User userOk;
    static User userWrong;
    static User userNotExist;
    static LogServlet logServlet;

    @BeforeClass
    public static void initialization() {
        logServlet = new LogServlet();
        userOk = new User("fluriko", "123456");
        userWrong = new User("fluriko", "1234");
        userNotExist = new User("freya", "123456");
        Database.addUser(userOk);
    }

    @AfterClass
    public static void clean() {
        Database.removeUser(userOk);
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