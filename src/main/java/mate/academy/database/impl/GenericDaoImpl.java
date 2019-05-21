package mate.academy.database.impl;

import mate.academy.database.GenericDao;
import mate.academy.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Optional;

public abstract class GenericDaoImpl<T> implements GenericDao<T> {
    Class<T> clazz;

    public GenericDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void add(T object) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
        session.close();
    }

    @Override
    public void remove(T object) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(object);
        transaction.commit();
        session.close();
    }

    @Override
    public void edit(T object) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(object);
        transaction.commit();
        session.close();
    }

    @Override
    public Optional<T> get(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        T object = session.get(clazz, id);
        session.close();
        return Optional.ofNullable(object);
    }

    @Override
    public List<T> getAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<T> objects = session.createQuery("From " + clazz.getName()).list();
        return objects;
    }
}
