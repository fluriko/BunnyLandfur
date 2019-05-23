package mate.academy.service.validator;

import mate.academy.database.UserDao;
import mate.academy.database.impl.UserDaoHibImpl;
import mate.academy.model.User;

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
}
