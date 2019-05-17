package mate.academy.model;

import java.util.List;
import java.util.Objects;

public class Cart {
    private Long id;
    private User user;
    private List<Good> goods;

    public Cart(Long id, User user, List<Good> goods) {
        this.id = id;
        this.user = user;
        this.goods = goods;
    }

    public Cart(User user, List<Good> goods) {
        this.user = user;
        this.goods = goods;
    }

    public Cart(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) &&
                Objects.equals(user, cart.user) &&
                Objects.equals(goods, cart.goods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, goods);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + user +
                ", goods=" + goods +
                '}';
    }
}
