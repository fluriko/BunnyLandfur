package mate.academy.database.good;

import mate.academy.model.Code;

import java.util.List;
import java.util.Optional;

public interface PurchaseCodeDao {
    int addCode(Code code);
    int removeCode(Code code);
    Optional<Code> getCode(String value);
    Optional<Code> getCode(Long id);
    List<Code> getCodes();
}
