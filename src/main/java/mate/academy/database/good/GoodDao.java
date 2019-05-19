package mate.academy.database.good;

import mate.academy.model.Good;
import java.util.List;
import java.util.Optional;

public interface GoodDao {
    int addGood(Good good);
    int removeGood(Good good);
    Optional<Good> getGood(Long id);
    int editGood(Good good);
    List<Good> getGoods();
}
