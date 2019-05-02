package mate.academy.model;

import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String password;
    private Roles role;
    private String mail;

    public User(int id, String name, String password, Roles role, String mail) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.mail = mail;
    }

    public User(String name, String password, Roles role, String mail) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.mail = mail;
    }

    public User(String name, String password, String mail) {
        this(name, password, Roles.USER, mail);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                role == user.role &&
                Objects.equals(mail, user.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, role, mail);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", mail='" + mail + '\'' +
                '}';
    }
}
