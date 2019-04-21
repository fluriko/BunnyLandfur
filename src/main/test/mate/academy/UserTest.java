package mate.academy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class UserTest {
    User user;
    User secondUser;

    @Before
    public void initialization() {
        user = new User("Nathan", "123456");
        secondUser = new User("Eliot", "123457");
    }

    @Test
    public void addUser() {
        User.addUser(secondUser);
        User.addUser(user);
        User expected = new User("Nathan", "123456");
        Assert.assertEquals(expected, User.getUsers().get(1));
    }

    @Test
    public void getUser() {
        User expected = new User("Eliot", "123457");
        Assert.assertEquals(expected, User.getUser(secondUser));
    }

    @Test
    public void getUsers() {
        List<User> expected = new ArrayList<>();
        expected.add(secondUser);
        expected.add(user);
        List<User> actual = User.getUsers();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getName() {
        String expected = "Nathan";
        String actual = user.getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getPassword() {
        String expected = "123456";
        String actual = user.getPassword();
        Assert.assertEquals(expected, actual);
    }
}