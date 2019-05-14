package mate.academy.database.user;

import mate.academy.model.User;
import mate.academy.util.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoHib implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoHib.class);

    @Override
    public int addUser(User user) {
        Session session;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        }
        try {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
            return 1;
        } catch (Exception e) {
            logger.debug("Error in adding user " + user.getLogin(), e);
            return 0;
        } finally {
            if (session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public int removeUser(User user) {
        Session session;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        }
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
            session.close();
            return 1;
        } catch (Exception e) {
            logger.debug("Error in removing user " + user.getId(), e);
            return 0;

        } finally {
            if (session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        Session session;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        }
        try {
            Criteria criteria = session.createCriteria(User.class);
            User user = (User) criteria.add(Restrictions.eq("login", login)).uniqueResult();
            return Optional.of(user);
        } catch (Exception e) {
            logger.debug("Error in getting user " + login, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserByMail(String mail) {
        Session session;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        }
        try {
            Criteria criteria = session.createCriteria(User.class);
            User user = (User) criteria.add(Restrictions.eq("mail", mail)).uniqueResult();
            return Optional.of(user);
        } catch (Exception e) {
            logger.debug("Error in getting user " + mail, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserById(Long id) {
        Session session;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        }
        try {
            User user = session.get(User.class, id);
            return Optional.of(user);
        } catch (Exception e) {
            logger.debug("Error in getting user " + id, e);
            return Optional.empty();
        } finally {
            if (session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public int editUser(User user) {
        Session session;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        }
        try {
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            session.close();
            return 1;
        } catch (Exception e) {
            logger.debug("Error in getting user " + user.getId(), e);
            return 0;
        } finally {
            if (session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public List<User> getUsers() {
        Session session;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        }
        List<User> users = new ArrayList<>();
        try {
            users = session.createQuery("From User").list();
        } catch (Exception e) {
            logger.debug("Error in getting all users", e);
        }
        return users;
    }
}
