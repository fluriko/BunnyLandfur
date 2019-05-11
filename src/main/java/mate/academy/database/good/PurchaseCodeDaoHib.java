package mate.academy.database.good;

import mate.academy.model.Code;
import mate.academy.util.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import java.util.List;
import java.util.Optional;

public class PurchaseCodeDaoHib implements PurchaseCodeDao {
    private static final Logger logger = Logger.getLogger(PurchaseCodeDaoHib.class);

    @Override
    public int addCode(Code code) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(code);
            transaction.commit();
            session.close();
            return 1;
        } catch (Exception e) {
            logger.debug("Error in adding code " + code.getValue(), e);
            return 0;
        }
    }

    @Override
    public int removeCode(Code code) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(code);
            transaction.commit();
            session.close();
            return 1;
        } catch (Exception e) {
            logger.debug("Error in removing code " + code.getValue(), e);
            return 0;
        }
    }

    @Override
    public Optional<Code> getCode(String value) {
        try {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Code.class);
            Code code = (Code) criteria.add(Restrictions.eq("value", value)).uniqueResult();
            return Optional.of(code);
        } catch (Exception e) {
            logger.debug("Error in getting code " + value, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Code> getCode(int id) {
        try {
            Code code = HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Code.class, id);
            return Optional.of(code);
        } catch (Exception e) {
            logger.debug("Error in getting code " + id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Code> getCodes() {
        List<Code> codes = (List<Code>)  HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("From Code")
                .list();
        return codes;
    }
}
