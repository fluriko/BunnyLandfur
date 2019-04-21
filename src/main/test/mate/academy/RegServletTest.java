package mate.academy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegServletTest {
    User user;
    User userOk;
    User userWrong;
    User userExist;
    RegServlet regServlet;

    @Before
    public void initialization() {
        regServlet = new RegServlet();
        user = new User("Nemesis", "678909");
        User.addUser(user);
        userOk = new User("fluriko", "123456");
        userWrong = new User("sol", "1234");
        userExist = new User("Nemesis", "567890");
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