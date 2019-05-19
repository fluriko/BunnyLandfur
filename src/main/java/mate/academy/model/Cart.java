package mate.academy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CARTS")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @OneToOne(mappedBy="cart", fetch= FetchType.EAGER)
    private User userOwner;

    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(
            name = "CART_GOOD",
            joinColumns = @JoinColumn(name = "CART_ID"),
            inverseJoinColumns = @JoinColumn(name = "GOOD_ID"))
    private List<Good> goodsInCart;

    public Cart(User userOwner) {
        this.userOwner = userOwner;
    }

    public Cart() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }

    public List<Good> getGoodsInCart() {
        return goodsInCart;
    }

    public void setGoodsInCart(List<Good> goodsInCart) {
        this.goodsInCart = goodsInCart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) &&
                Objects.equals(userOwner, cart.userOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userOwner);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userOwner=" + userOwner +
                ", goodsInCart=" + goodsInCart +
                '}';
    }
}
