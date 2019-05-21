package mate.academy.database.impl;

import mate.academy.database.UserDao;
import mate.academy.model.User;
import mate.academy.util.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.Optional;

public class UserDaoHibImpl extends GenericDaoImpl<User> implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoHibImpl.class);

    public UserDaoHibImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        try (Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()) {
            Query query = session.createQuery("FROM User WHERE login = :login");
            query.setParameter("login", login);
            User user = (User) query.uniqueResult();
            return Optional.ofNullable(user);
        } catch (Exception e) {
            logger.error("Error in getting user by login", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserByMail(String mail) {
        try (Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()) {
            Query query = session.createQuery("FROM User WHERE mail = :mail");
            query.setParameter("mail", mail);
            User user = (User) query.uniqueResult();
            return Optional.ofNullable(user);
        } catch (Exception e) {
            logger.error("Error in getting user by mail", e);
            return Optional.empty();
        }
    }
}
