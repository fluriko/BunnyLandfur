package mate.academy.model;

import mate.academy.util.HashUtil;

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
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @Column(name = "MAIL")
    private String mail;

    @Column(name = "SALT")
    private String salt;

    public User(Long id, String login, String password, Role role, String mail, String salt) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.mail = mail;
        this.salt = salt;
    }

    public User(String login, String password, Role role, String mail) {
        this.salt = HashUtil.getRandomSalt();
        this.login = login;
        this.password = HashUtil.getSha512SecurePassword(password, salt);
        this.role = role;
        this.mail = mail;
    }

    public User(String login, String password, String mail) {
        this(login, password, new Role(2L, "USER"), mail);
    }

    public User() {
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

    public void setSalt() {
        this.salt = HashUtil.getRandomSalt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(role, user.role) &&
                Objects.equals(mail, user.mail) &&
                Objects.equals(salt, user.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role, mail, salt);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", mail='" + mail + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
