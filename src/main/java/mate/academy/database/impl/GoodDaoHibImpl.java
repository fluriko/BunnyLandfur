package mate.academy.database.impl;

import mate.academy.database.GoodDao;
import mate.academy.model.Good;

public class GoodDaoHibImpl extends GenericDaoImpl<Good> implements GoodDao {
    public GoodDaoHibImpl() {
        super(Good.class);
    }
}
