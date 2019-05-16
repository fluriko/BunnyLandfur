package mate.academy.database.user;

import mate.academy.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoHibTest {
    UserDaoHib userDaoHib;
    User userOne;
    User userTwo;
    User userThree;
    User userFour;
    User userFive;

    @Before
    public void init() {
        userDaoHib = new UserDaoHib();
        userOne = new User("111111", "111111", "test1@test.com");
        userTwo = new User("222222", "222222", "test2@test.com");
        userThree = new User("333333", "333333", "test3@test.com");
        userFour = new User("444444", "444444", "test4@test.com");
        userFive = new User("555555", "555555", "test5@test.com");

    }

    @After
    public void clean() {
        userDaoHib.removeUser(userOne);
        userDaoHib.removeUser(userTwo);
        userDaoHib.removeUser(userThree);
        userDaoHib.removeUser(userFour);
        userDaoHib.removeUser(userFive);
    }

    @Test
    public void addUser() {
        int actual = userDaoHib.addUser(userOne);
        Assert.assertEquals(1, actual);
    }

    @Test
    public void removeUser() {
        userDaoHib.addUser(userOne);
        int actual = userDaoHib.removeUser(userOne);
        Assert.assertEquals(1, actual);
    }

    @Test
    public void getUserByLogin() {
        userDaoHib.addUser(userTwo);
        Optional<User> optionalUser = userDaoHib.getUserByLogin(userTwo.getLogin());
        Assert.assertNotEquals(optionalUser, Optional.empty());
    }

    @Test
    public void getUserByMail() {
        userDaoHib.addUser(userThree);
        Optional<User> optionalUser = userDaoHib.getUserByMail(userThree.getMail());
        Assert.assertNotEquals(optionalUser, Optional.empty());
    }

    @Test
    public void getUserById() {
        userDaoHib.addUser(userFour);
        Long id = userDaoHib.getUserByLogin(userFour.getLogin()).get().getId();
        Optional<User> optionalUser = userDaoHib.getUserById(id);
        Assert.assertNotEquals(optionalUser, Optional.empty());
    }

    @Test
    public void editUser() {
        userDaoHib.addUser(userFive);
        userFive.setPassword("111111");
        int actual = userDaoHib.editUser(userFive);
        Assert.assertEquals(1, actual);
    }

    @Test
    public void getUsers() {
        User fluriko = userDaoHib.getUserByLogin("fluriko").get();
        User candy = userDaoHib.getUserByLogin("candy").get();
        User sweetie = userDaoHib.getUserByLogin("sweetie").get();
        List<User> expected = new ArrayList<>();
        expected.add(fluriko);
        expected.add(candy);
        expected.add(sweetie);
        List<User> actual = userDaoHib.getUsers();
        Assert.assertEquals(expected, actual);
    }
}