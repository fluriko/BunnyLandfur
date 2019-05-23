package mate.academy.database.impl;

import mate.academy.database.GenericDao;
import mate.academy.util.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class GenericDaoImpl<T> implements GenericDao<T> {
    private static final Logger logger = Logger.getLogger(GenericDaoImpl.class);
    Class<T> clazz;

    public GenericDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean add(T object) {
        try (Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
            return true;
        } catch (Exception e) {
            logger.error("Error in adding: " + clazz.getName(), e);
            return false;
        }
    }

    @Override
    public boolean remove(T object) {
        try (Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(object);
            transaction.commit();
            return true;
        } catch (Exception e) {
            logger.error("Error in removing: " + clazz.getName(), e);
            return false;
        }
    }

    @Override
    public boolean edit(T object) {
        try (Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(object);
            transaction.commit();
            return true;
        } catch (Exception e) {
            logger.error("Error in editing: " + clazz.getName(), e);
            return false;
        }
    }

    @Override
    public Optional<T> get(long id) {
        try (Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()) {
            T object = session.get(clazz, id);
            return Optional.ofNullable(object);
        } catch (Exception e) {
            logger.error("Error in getting: " + clazz.getName(), e);
            return Optional.empty();
        }
    }

    @Override
    public List<T> getAll() {
        try (Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()) {
            List<T> objects = session
                    .createQuery("From " + clazz.getName())
                    .list();
            return objects;
        } catch (Exception e) {
            logger.error("Error in getting all: " + clazz.getName(), e);
            return new ArrayList<>();
        }
    }
}
