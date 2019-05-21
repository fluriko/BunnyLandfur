package mate.academy.database.impl;

import mate.academy.database.PurchaseCodeDao;
import mate.academy.model.Code;
import mate.academy.util.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.Optional;

public class PurchaseCodeDaoHibImpl extends GenericDaoImpl<Code> implements PurchaseCodeDao {
    private static final Logger logger = Logger.getLogger(PurchaseCodeDaoHibImpl.class);

    public PurchaseCodeDaoHibImpl() {
        super(Code.class);
    }

    @Override
    public Optional<Code> getCodeByValue(String value) {
        try (Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()) {
            Query query = session.createQuery("FROM Code WHERE value = :value");
            query.setParameter("value", value);
            Code code = (Code) query.uniqueResult();
            return Optional.ofNullable(code);
        } catch (Exception e) {
            logger.error("Error in getting code by value", e);
            return Optional.empty();
        }
    }
}
