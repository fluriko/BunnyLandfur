package mate.academy.database;

import mate.academy.database.user.UserDao;
import mate.academy.database.user.UserDaoJdbc;
import mate.academy.model.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class UserDaoTest {
    private static final UserDao USER_DAO = new UserDaoJdbc();
    static User user;
    static User userToRemove;
    static User userToAdd;

    @Before
    public void initialization() {
        user = new User("Alice", "123456", "123456@gmail.com");
        userToRemove = new User("Mary", "111111", "234567@gmail.com");
        userToAdd = new User("Sophie", "232323", "345678@gmail.com");
        USER_DAO.addUser(user);
    }

    @After
    public void cleanPart() {
        USER_DAO.removeUser(userToRemove);
        USER_DAO.removeUser(userToAdd);
    }

    @AfterClass
    public static void clean() {
        USER_DAO.removeUser(user);
        USER_DAO.removeUser(userToRemove);
        USER_DAO.removeUser(userToAdd);
    }

    @Test
    public void addUser() {
        USER_DAO.addUser(userToAdd);
        Assert.assertTrue(USER_DAO.containsLogin(userToAdd.getLogin()));
    }

    @Test
    public void removeUser() {
        USER_DAO.addUser(userToRemove);
        Assert.assertNotEquals(USER_DAO.removeUser(userToRemove), 0);
    }

    @Test
    public void getUser() {
        Optional<User> userOptional = USER_DAO.getUserByLogin(user.getLogin());
        Assert.assertTrue(userOptional.isPresent());
    }

    @Test
    public void contains() {
        Assert.assertTrue(USER_DAO.containsLogin(user.getLogin()));
    }
}