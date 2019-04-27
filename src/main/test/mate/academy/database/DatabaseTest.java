package mate.academy.database;

import mate.academy.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTest {
    static User user;

    @BeforeClass
    public static void initialization() {
        user = new User("Alice", "123456");
    }

    @After
    public void delete() {
        Database.removeAll();
    }

    @Test
    public void addUser() {
        User another = new User("Alice", "123456");
        Database.addUser(user);
        Assert.assertEquals(another, Database.getUser(user));
    }

    @Test
    public void removeUser() {
        Database.addUser(user);
        Database.removeUser(user);
        Assert.assertFalse(Database.contains(user));
    }

    @Test
    public void getUser() {
        Database.addUser(user);
        Assert.assertTrue(Database.contains(user));
    }

    @Test
    public void editUser() {
        Database.addUser(user);
        String test = "456456";
        Database.editUser(user, test);
        Assert.assertEquals(test, Database.getUser(user).getPassword());
    }

    @Test
    public void getUsers() {
        Database.addUser(user);
        List<User> users = new ArrayList<>();
        users.add(user);
        Assert.assertEquals(users, Database.getUsers());
    }

    @Test
    public void contains() {
        Database.addUser(user);
        Assert.assertTrue(Database.contains(user));
    }
}