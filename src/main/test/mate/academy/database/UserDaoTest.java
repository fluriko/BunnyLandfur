package mate.academy.database;

import mate.academy.model.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoTest {
    private static final UserDao USER_DAO = new UserDao();
    static User user;
    static User userToRemove;
    static User userToAdd;

    @Before
    public void initialization() {
        user = new User("Alice", "123456");
        userToRemove = new User("Mary", "111111");
        userToAdd = new User("Sophie", "232323");
        USER_DAO.addUser(user);
    }

    @After
    public void cleanPart() {
        USER_DAO.removeUser(userToRemove.getName());
        USER_DAO.removeUser(userToAdd.getName());
    }

    @AfterClass
    public static void clean() {
        USER_DAO.removeUser(user.getName());
        USER_DAO.removeUser(userToRemove.getName());
        USER_DAO.removeUser(userToAdd.getName());
    }

    @Test
    public void addUser() {
        USER_DAO.addUser(userToAdd);
        Assert.assertTrue(USER_DAO.contains(userToAdd.getName()));
    }

    @Test
    public void removeUser() {
        USER_DAO.addUser(userToRemove);
        Assert.assertNotEquals(USER_DAO.removeUser(userToRemove.getName()), 0);
    }

    @Test
    public void getUser() {
        Optional<User> userOptional = USER_DAO.getUser(user.getName());
        Assert.assertTrue(userOptional.isPresent());
    }

    @Test
    public void editUser() {
        Assert.assertNotEquals(USER_DAO.editUser(user.getName(), "password"), 0);
    }

    @Test
    public void setRole() {
        Assert.assertTrue(USER_DAO.setRole(user.getName(), 2));
    }

    @Test
    public void contains() {
        Assert.assertTrue(USER_DAO.contains(user.getName()));
    }
}