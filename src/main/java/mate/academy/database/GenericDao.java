package mate.academy.database;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    int add(T object);
    int remove(T object);
    int edit(T object);
    Optional<T> get(long id);
    List<T> getAll(); //TODO MAKE SETS
}
