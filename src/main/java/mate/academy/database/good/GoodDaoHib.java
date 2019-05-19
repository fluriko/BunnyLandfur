package mate.academy.database.good;

import mate.academy.model.Good;
import mate.academy.util.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Optional;

public class GoodDaoHib implements GoodDao {
    private static final Logger logger = Logger.getLogger(GoodDaoHib.class);

    @Override
    public int addGood(Good good) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(good);
        transaction.commit();
        session.close();
        return 1;
    }

    @Override
    public int removeGood(Good good) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(good);
        transaction.commit();
        session.close();
        return 1;
    }

    @Override
    public Optional<Good> getGood(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Good good = session.get(Good.class, id);
        session.close();
        return Optional.ofNullable(good);
    }

    @Override
    public int editGood(Good good) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(good);
        transaction.commit();
        session.close();
        return 1;
    }

    @Override
    public List<Good> getGoods() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Good> goods = session.createQuery("From Good").list();
        return goods;
    }
}
