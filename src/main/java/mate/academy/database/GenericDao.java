package mate.academy.database;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    void add(T object);
    void remove(T object);
    void edit(T object);
    Optional<T> get(long id);
    List<T> getAll();
}
