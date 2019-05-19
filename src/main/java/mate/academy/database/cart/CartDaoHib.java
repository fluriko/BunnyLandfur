package mate.academy.database.cart;

import mate.academy.model.Cart;
import mate.academy.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Optional;

public class CartDaoHib implements CartDao {
    @Override
    public int addCart(Cart cart) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(cart);
        transaction.commit();
        session.close();
        return 1;
    }

    @Override
    public int editCart(Cart cart) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(cart);
        transaction.commit();
        session.close();
        return 1;
    }

    @Override
    public int removeCart(Cart cart) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(cart);
        transaction.commit();
        session.close();
        return 1;
    }

    @Override
    public Optional<Cart> getCart(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Cart cart = session.get(Cart.class, id);
        session.close();
        return Optional.ofNullable(cart);
    }

    @Override
    public List<Cart> getCarts() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Cart> carts = session.createQuery("From Cart").list();
        return carts;
    }
}
