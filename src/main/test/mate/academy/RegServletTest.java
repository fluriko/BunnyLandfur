package mate.academy;

import org.junit.Test;

import static org.junit.Assert.*;

public class RegServletTest {
    @Test
    public void checkUser() {
        User userOk = new User("fluriko", "123456");
        User userWrong = new User("sol", "1234");
        User userExist = new User("fluriko", "567890");
        RegServlet regServlet = new RegServlet();
        String expected = regServlet.checkUser(userOk);
        String actual = "greeting.jsp";
        assertEquals(actual, expected);
        expected = regServlet.checkUser(userWrong);
        actual = "wrongreg.jsp";
        assertEquals(actual, expected);
        User.addUser(userOk);
        expected = regServlet.checkUser(userExist);
        actual = "exist.jsp";
        assertEquals(actual, expected);
    }
}