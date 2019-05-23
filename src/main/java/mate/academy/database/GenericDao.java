package mate.academy.database;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    boolean add(T object);
    boolean remove(T object);
    boolean edit(T object);
    Optional<T> get(long id);
    List<T> getAll(); //TODO MAKE SETS
}
