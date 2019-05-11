package mate.academy.model;

import mate.academy.util.HashUtil;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role_id")
    private int roleId;

    @Column(name = "mail")
    private String mail;

    @Column(name = "salt")
    private String salt;

    public User(int id, String login, String password, int roleId, String mail, String salt) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.roleId = roleId;
        this.mail = mail;
        this.salt = salt;
    }

    public User(String login, String password, int roleId, String mail) {
        this.login = login;
        this.password = password;
        this.roleId = roleId;
        this.mail = mail;
        this.salt = HashUtil.getRandomSalt();
    }

    public User(String login, String password, String mail) {
        this(login, password, Roles.USER.getId(), mail);
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                roleId == user.roleId &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(mail, user.mail) &&
                Objects.equals(salt, user.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, roleId, mail, salt);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                ", mail='" + mail + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
