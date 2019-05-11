package mate.academy.database.role;

import mate.academy.model.Role;
import mate.academy.util.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import java.util.List;
import java.util.Optional;

public class RoleDaoHib implements RoleDao {
    private static final Logger logger = Logger.getLogger(RoleDaoHib.class);

    @Override
    public int addRole(Role role) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
            session.close();
            return 1;
        } catch (Exception e) {
            logger.debug("Error in adding role " + role.getName(), e);
            return 0;
        }
    }

    @Override
    public int removeRole(Role role) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(role);
            transaction.commit();
            session.close();
            return 1;
        } catch (Exception e) {
            logger.debug("Error in removing role " + role.getName(), e);
            return 0;
        }
    }

    @Override
    public int editRole(Role role) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(role);
            transaction.commit();
            session.close();
            return 1;
        } catch (Exception e) {
            logger.debug("Error in getting role " + role.getName(), e);
            return 0;
        }
    }

    @Override
    public Optional<Role> getRole(int id) {
        try {
            Role role = HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Role.class, id);
            return Optional.of(role);
        } catch (Exception e) {
            logger.debug("Error in getting role " + id, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Role> getRole(String name) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Role.class);
            Role user = (Role) criteria.add(Restrictions.eq("name", name)).uniqueResult();
            return Optional.of(user);
        } catch (Exception e) {
            logger.debug("Error in getting role " + name, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Role> getRoles() {
        List<Role> roles = (List<Role>)  HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("From Role")
                .list();
        return roles;
    }
}
