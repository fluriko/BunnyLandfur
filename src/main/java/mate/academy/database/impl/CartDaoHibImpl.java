package mate.academy.database.impl;

import mate.academy.database.CartDao;
import mate.academy.model.Cart;

public class CartDaoHibImpl extends GenericDaoImpl<Cart> implements CartDao {
    public CartDaoHibImpl() {
        super(Cart.class);
    }
}
