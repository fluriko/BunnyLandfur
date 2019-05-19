package mate.academy.database.user;

import mate.academy.model.User;
import mate.academy.util.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Optional;

public class UserDaoHib implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoHib.class);

    @Override
    public int addUser(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query queryLogin = session.createQuery("FROM User WHERE login = :login");
        queryLogin.setParameter("login", user.getLogin());
        List<User> existLogin = queryLogin.list();
        Query queryMail = session.createQuery("FROM User WHERE mail = :mail");
        queryMail.setParameter("mail", user.getMail());
        List<User> existMail = queryMail.list();
        if (existLogin.size() == 0 && existMail.size() == 0) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
            return 1;
        }
        session.close();
        return 0;
    }

//    @Override
//    public int removeUser(User user) {
//        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
//        String hql = "DELETE FROM User WHERE ID = :ID";
//        Query queryRemove = session.createQuery(hql);
//        queryRemove.setParameter("ID", user.getId());
//        int result = queryRemove.executeUpdate();
//        session.close();
//        return result;
//    }

    @Override
    public int removeUser(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
        return 1;
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

    @Override
    public Optional<User> getUserById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        User user = session.get(User.class, id);
        session.close();
        return Optional.ofNullable(user);
    }

//    @Override
//    public int editUser(User user) {
//        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
//        User userById = session.get(User.class, user.getId());
//        Query queryLogin = session.createQuery("FROM User WHERE login = :login");
//        queryLogin.setParameter("login", user.getLogin());
//        List<User> existLogin = queryLogin.list();
//        Query queryMail = session.createQuery("FROM User WHERE mail = :mail");
//        queryMail.setParameter("mail", user.getMail());
//        List<User> existMail = queryMail.list();
//        boolean isUserExist = userById != null;
//        boolean isLoginUniq = existLogin.size() == 0 || existLogin.get(0).getId().equals(user.getId());
//        boolean isMailUniq = existMail.size() == 0 || existMail.get(0).getId().equals(user.getId());
//        session.close();
//        if (isUserExist && isLoginUniq && isMailUniq) {
//            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
//            String hql = "UPDATE User SET " +
//                    "LOGIN = :LOGIN, " +
//                    "PASSWORD = :PASSWORD, " +
//                    "ROLE_ID = :ROLE_ID, " +
//                    "MAIL = :MAIL, " +
//                    "SALT = :SALT " +
//                    "WHERE ID = :ID";
//            Query queryUpdate = session.createQuery(hql);
//            queryUpdate.setParameter("ID", user.getId());
//            queryUpdate.setParameter("LOGIN", user.getLogin());
//            queryUpdate.setParameter("PASSWORD", user.getPassword());
//            queryUpdate.setParameter("ROLE_ID", user.getRole().getId());
//            queryUpdate.setParameter("MAIL", user.getMail());
//            queryUpdate.setParameter("SALT", user.getSalt());
//            int result = queryUpdate.executeUpdate();
//            session.close();
//            return result;
//        }
//        return 0;
//    }

    @Override
    public int editUser(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query queryId = session.createQuery("FROM User WHERE id = :id");
        queryId.setParameter("id", user.getId());
        User userById = session.get(User.class, user.getId());
        Query queryLogin = session.createQuery("FROM User WHERE login = :login");
        queryLogin.setParameter("login", user.getLogin());
        List<User> existLogin = queryLogin.list();
        Query queryMail = session.createQuery("FROM User WHERE mail = :mail");
        queryMail.setParameter("mail", user.getMail());
        List<User> existMail = queryMail.list();
        boolean isUserExist = userById != null;
        boolean isLoginUniq = existLogin.size() == 0 || existLogin.get(0).getId().equals(user.getId());
        boolean isMailUniq = existMail.size() == 0 || existMail.get(0).getId().equals(user.getId());
        session.close();
        if (isUserExist && isLoginUniq && isMailUniq) {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            session.close();
            return 1;
        }
        return 0;
    }

    @Override
    public List<User> getUsers() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<User> users = session.createQuery("From User").list();
        return users;
    }
}
