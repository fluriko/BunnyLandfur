package mate.academy.database.impl;

import mate.academy.database.PurchaseCodeDao;
import mate.academy.model.Code;
import mate.academy.util.HibernateSessionFactoryUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.Optional;

public class PurchaseCodeDaoHibImpl extends GenericDaoImpl<Code> implements PurchaseCodeDao {
    public PurchaseCodeDaoHibImpl() {
        super(Code.class);
    }

    @Override
    public Optional<Code> getCodeByValue(String value) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Code WHERE value = :value");
        query.setParameter("value", value);
        Code code = (Code) query.uniqueResult();
        session.close();
        return Optional.ofNullable(code);
    }
}
