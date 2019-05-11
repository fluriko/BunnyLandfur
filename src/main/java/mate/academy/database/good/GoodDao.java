package mate.academy.database.good;

import mate.academy.model.Good;

import java.util.List;
import java.util.Optional;

public interface GoodDao {
    Long addGood(Good good);
    Long removeGood(Good good);
    Optional<Good> getGood(Long id);
    Long editGood(Good good);
    List<Good> getGoods();
}
