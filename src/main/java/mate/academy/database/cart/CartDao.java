package mate.academy.database.cart;

import mate.academy.model.Cart;
import java.util.List;
import java.util.Optional;

public interface CartDao {
    int addCart(Cart cart);
    int editCart(Cart cart);
    int removeCart(Cart cart);
    Optional<Cart> getCart(Long id);
    List<Cart> getCarts();
}
