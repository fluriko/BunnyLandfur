package mate.academy.database.role;

import mate.academy.model.Role;
import mate.academy.util.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Optional;

public class RoleDaoHib implements RoleDao {
    private static final Logger logger = Logger.getLogger(RoleDaoHib.class);

    @Override
    public int addRole(Role role) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(role);
        transaction.commit();
        session.close();
        return 1;
    }

    @Override
    public int removeRole(Role role) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(role);
        transaction.commit();
        session.close();
        return 1;
    }

    @Override
    public int editRole(Role role) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(role);
        transaction.commit();
        session.close();
        return 1;
    }

    @Override
    public Optional<Role> getRole(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Role role = session.get(Role.class, id);
        session.close();
        return Optional.ofNullable(role);
    }

    @Override
    public List<Role> getRoles() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Role> roles = session.createQuery("From Role").list();
        return roles;
    }
}
