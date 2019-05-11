package mate.academy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "codes")
public class Code {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_id")
    private Good good;

    public Code(int id, String value, User user, Good good) {
        this.id = id;
        this.value = value;
        this.user = user;
        this.good = good;
    }

    public Code(String value, User user, Good good) {
        this.value = value;
        this.user = user;
        this.good = good;
    }

    public Code() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Code)) return false;
        Code code = (Code) o;
        return id == code.id &&
                Objects.equals(value, code.value) &&
                Objects.equals(user, code.user) &&
                Objects.equals(good, code.good);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, user, good);
    }

    @Override
    public String toString() {
        return "Code{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", user=" + user +
                ", good=" + good +
                '}';
    }
}
