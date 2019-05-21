package mate.academy.database;

import mate.academy.model.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> getUserByLogin(String login);
    Optional<User> getUserByMail(String mail);
}
