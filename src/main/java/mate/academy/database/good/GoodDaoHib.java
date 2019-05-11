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
    public Long addGood(Good good) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(good);
            transaction.commit();
            session.close();
            return 1L;
        } catch (Exception e) {
            logger.debug("Error in adding good " + good.getLabel(), e);
            return 0L;
        }
    }

    @Override
    public Long removeGood(Good good) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(good);
            transaction.commit();
            session.close();
            return 1L;
        } catch (Exception e) {
            logger.debug("Error in removing user " + good.getId(), e);
            return 0L;
        }
    }

    @Override
    public Optional<Good> getGood(Long id) {
        try {
            Good good = HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Good.class, id);
            return Optional.of(good);
        } catch (Exception e) {
            logger.debug("Error in getting user " + id, e);
            return Optional.empty();
        }
    }

    @Override
    public Long editGood(Good good) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(good);
            transaction.commit();
            session.close();
            return 1L;
        } catch (Exception e) {
            logger.debug("Error in getting user " + good.getId(), e);
            return 0L;
        }
    }

    @Override
    public List<Good> getGoods() {
        List<Good> goods = (List<Good>)  HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("From Good")
                .list();
        return goods;
    }
}
