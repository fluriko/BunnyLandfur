package mate.academy.service.validator;

import mate.academy.database.UserDao;
import mate.academy.database.impl.UserDaoHibImpl;
import mate.academy.model.User;

public class UserValidationService extends ValidationService<User> {
    private static final UserDao userDao = new UserDaoHibImpl();

    public String validateUniq(User user) {
        String result = "";
        if (userDao.getUserByLogin(user.getLogin()).isPresent()) {
            result += "login is not uniq | ";
        }
        if (userDao.getUserByMail(user.getMail()).isPresent()) {
            result += "mail is not uniq | ";
        }
        return result;
    }

    @Override
    public String validate(User object) {
        String result = super.validate(object);
        if (result.isEmpty()) {
            return validateUniq(object);
        }
        return result;
    }
}
