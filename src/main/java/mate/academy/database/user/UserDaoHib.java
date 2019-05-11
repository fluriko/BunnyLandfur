package mate.academy.database.user;

import mate.academy.model.User;
import mate.academy.util.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import java.util.List;
import java.util.Optional;

public class UserDaoHib implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoJdbc.class);

    @Override
    public int addUser(User user) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
            return 1;
        } catch (Exception e) {
            logger.debug("Error in adding user " + user.getLogin(), e);
            return 0;
        }
    }

    @Override
    public int removeUser(User user) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
            session.close();
            return 1;
        } catch (Exception e) {
            logger.debug("Error in removing user " + user.getId(), e);
            return 0;
        }
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(User.class);
            User user = (User) criteria.add(Restrictions.eq("login", login)).uniqueResult();
            return Optional.of(user);
        } catch (Exception e) {
            logger.debug("Error in getting user " + login, e);
            return Optional.empty();
        }
    }

    public Optional<User> getUserByMail(String mail) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(User.class);
            User user = (User) criteria.add(Restrictions.eq("mail", mail)).uniqueResult();
            return Optional.of(user);
        } catch (Exception e) {
            logger.debug("Error in getting user " + mail, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserById(int id) {
        try {
            User user = HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
            return Optional.of(user);
        } catch (Exception e) {
            logger.debug("Error in getting user " + id, e);
            return Optional.empty();
        }
    }

    @Override
    public int editUser(User user) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            session.close();
            return 1;
        } catch (Exception e) {
            logger.debug("Error in getting user " + user.getId(), e);
            return 0;
        }
    }

    @Override
    public List<User> getUsers() {
        List<User> users = (List<User>)  HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("From User")
                .list();
        return users;
    }

    @Override
    public boolean containsLogin(String login) {
        return getUserByLogin(login).isPresent();
    }

    @Override
    public boolean containsMail(String mail) {
        return getUserByMail(mail).isPresent();
    }
}
