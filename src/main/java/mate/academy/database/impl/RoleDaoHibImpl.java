package mate.academy.database.impl;

import mate.academy.database.RoleDao;
import mate.academy.model.Role;

public class RoleDaoHibImpl extends GenericDaoImpl<Role> implements RoleDao {
    public RoleDaoHibImpl() {
        super(Role.class);
    }
}
