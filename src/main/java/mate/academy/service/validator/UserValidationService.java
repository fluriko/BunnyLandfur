package mate.academy.service.validator;

import mate.academy.database.UserDao;
import mate.academy.database.impl.UserDaoHibImpl;
import mate.academy.model.User;
import mate.academy.util.HashUtil;
import java.util.Optional;

public class UserValidationService extends GenericValidationService<User> {
    private static final UserDao userDao = new UserDaoHibImpl();

    public String checkUniq(String login, String mail) {
        String result = "";
        if (userDao.getUserByLogin(login).isPresent()) {
            result += "login is not unique | ";
        }
        if (userDao.getUserByMail(mail).isPresent()) {
            result += "mail is not unique | ";
        }
        return result;
    }

    public String validateDataForLogIn(String login, String password) {
        String result = "";
        Optional<User> userOptional = userDao.getUserByLogin(login);
        if (!userOptional.isPresent()) {
            result = "login is not in base | ";
        } else {
            User userFromDb = userOptional.get();
            String passwordHash = HashUtil.getSha512SecurePassword(password, userFromDb.getSalt());
            result = validatePasswordForLogIn(userFromDb.getPassword(), passwordHash);
        }
        return result;
    }

    private String validatePasswordForLogIn(String passwordFromDb, String passwordFromForm) {
        if (passwordFromDb.equals(passwordFromForm)) {
            return "";
        } else {
            return "wrong login/password | ";
        }
    }
}
