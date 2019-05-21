package mate.academy.database;

import mate.academy.model.Code;
import java.util.Optional;

public interface PurchaseCodeDao extends GenericDao<Code> {
    Optional<Code> getCodeByValue(String value);
}
