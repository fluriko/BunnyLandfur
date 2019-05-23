package mate.academy.model;

import mate.academy.util.HashUtil;
import org.hibernate.validator.constraints.Length;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @Length(min = 4, max = 16, message = "incorrect login |")
    @Column(name = "LOGIN", unique = true, nullable = false)
    private String login;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @ManyToOne(optional=false, fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @Email(message = "incorrect mail |")
    @Column(name = "MAIL", unique = true, nullable = false)
    private String mail;

    @Column(name = "SALT", nullable = false)
    private String salt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @Transient
    @Min(value = 6, message = "too short password |")
    @Max(value = 16, message = "too long password |")
    private int passwordLength;

    public User(String login, String password, Role role, String mail) {
        this.salt = HashUtil.getRandomSalt();
        this.login = login;
        this.password = HashUtil.getSha512SecurePassword(password, salt);
        this.role = role;
        this.mail = mail;
        cart = new Cart(this);
        passwordLength = password.length();
    }

    public User(String login, String password, String mail) {
        this(login, password, new Role(2L, "USER"), mail);
    }

    public User() {
    }

    public int getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
    }

    public void addGoodToCart(Good good) {
        cart.addGood(good);
    }

    public List<Good> getGoodsInCart() {
        return cart.getGoodsInCart();
    }

    public void removeFromCart(Good good) {
        cart.removeGood(good);
    }

    public void editGoodInCart(Good good) {
        cart.editGood(good);
    }

    public void cleanCart() {
        cart.clear();
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = HashUtil.getSha512SecurePassword(password, salt);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getInfo() {
        return role.getName() + " " + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(role.getId(), user.role.getId()) &&
                Objects.equals(mail, user.mail) &&
                Objects.equals(salt, user.salt) &&
                Objects.equals(cart.getId(), user.cart.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role.getId(), mail, salt, cart.getId());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password.substring(0, 7) + '\'' +
                ", role=" + role.getId() +
                ", mail='" + mail + '\'' +
                ", salt='" + salt + '\'' +
                ", cart=" + cart.getId() +
                '}';
    }
}
