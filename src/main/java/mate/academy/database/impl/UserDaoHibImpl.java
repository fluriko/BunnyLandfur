package mate.academy.database.impl;

import mate.academy.database.UserDao;
import mate.academy.model.User;
import mate.academy.util.HibernateSessionFactoryUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.Optional;

public class UserDaoHibImpl extends GenericDaoImpl<User> implements UserDao {
    public UserDaoHibImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("FROM User WHERE login = :login");
        query.setParameter("login", login);
        User user = (User) query.uniqueResult();
        session.close();
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> getUserByMail(String mail) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("FROM User WHERE mail = :mail");
        query.setParameter("mail", mail);
        User user = (User) query.uniqueResult();
        session.close();
        return Optional.ofNullable(user);
    }
}
