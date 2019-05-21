package mate.academy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "CODES")
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "VALUE", unique = true, nullable = false)
    private String value;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CART_ID", nullable = false)
    private Cart cart;

    public Code(Long id, String value, Cart cart) {
        this.id = id;
        this.value = value;
        this.cart = cart;
    }

    public Code(String value, Cart cart) {
        this.value = value;
        this.cart = cart;
    }

    public Code() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Code)) return false;
        Code code = (Code) o;
        return Objects.equals(id, code.id) &&
                Objects.equals(value, code.value) &&
                Objects.equals(cart.getId(), code.cart.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, cart.getId());
    }

    @Override
    public String toString() {
        return "Code{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", cart=" + cart +
                '}';
    }
}
