package mate.academy;

import mate.academy.database.Database;
import mate.academy.model.User;
import mate.academy.servlets.RegServlet;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RegServletTest {
    static User user;
    static User userOk;
    static User userWrong;
    static User userExist;
    static RegServlet regServlet;

    @BeforeClass
    public static void initialization() {
        regServlet = new RegServlet();
        user = new User("Nemesis", "678909");
        Database.addUser(user);
        userOk = new User("fluriko", "123456");
        userWrong = new User("sol", "1234");
        userExist = new User("Nemesis", "567890");
    }

    @AfterClass
    public static void clean() {
        Database.removeUser(user);
    }

    @Test
    public void checkUserOk() {
        String expected = regServlet.checkUser(userOk);
        String actual = "greeting.jsp";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void checkUserWrong() {
        String expected = regServlet.checkUser(userWrong);
        String actual = "wrongreg.jsp";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void checkUserExist() {
        String expected = regServlet.checkUser(userExist);
        String actual = "exist.jsp";
        Assert.assertEquals(actual, expected);
    }
}