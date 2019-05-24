package mate.academy.database.impl;

import mate.academy.database.OrderDao;
import mate.academy.model.Order;

public class OrderDaoHibImpl extends GenericDaoImpl<Order> implements OrderDao {
    public OrderDaoHibImpl() {
        super(Order.class);
    }
}
