package mate.academy.database.role;

import mate.academy.model.Role;
import java.util.List;
import java.util.Optional;

public interface RoleDao {
    int addRole(Role role);
    int removeRole(Role role);
    int editRole(Role role);
    Optional<Role> getRole(Long id);
    Optional<Role> getRole(String name);
    List<Role> getRoles();
}
